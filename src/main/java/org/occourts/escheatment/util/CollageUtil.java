package org.occourts.escheatment.util;

import java.io.File;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.occourts.escheatment.Constants;
import org.ocsc.collage.CollageSecureClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Collage utility class $Revision: 4531 $ $Author: flabriaga $ $Date:
 * 2018-10-11 08:52:47 -0700 (Wed, 10 Oct 2018) $
 */

public final class CollageUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CollageUtil.class);
	public static final String HEADER_ACCEPT = "Accept";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_MULTI_PART = "multipart/form-data";

	private GenericObjectPool<HttpClient> pooledHttpClient;
	private volatile static CollageUtil instance;
	private final String collageSession;
	private final CollageSecureClient collageSecureClient;

	private CollageUtil() {
		super();
		// Initiate Collage session
		collageSecureClient = new CollageSecureClient();
		collageSecureClient.setConnectionInfo(getEnvironment(),
				SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_AUTHENTICATE),
				SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_AUTHORIZE));
		collageSession = collageSecureClient.getSecureSessionid();
		LOGGER.info("Collage session returned [{}]", collageSession);

		pooledHttpClient = new GenericObjectPool<HttpClient>(new HttpClientFactory());
		pooledHttpClient.setTestOnBorrow(true);
	}

	public HttpClient getHttpClient() {
		try {
			return pooledHttpClient.borrowObject();
		} catch (Exception e) {
			LOGGER.warn("Unable to get HttpClient instance error message[{}]", e.getMessage());
		}
		return null;
	}

	private String getEnvironment() {
		String env = SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_ENV);
		return env;
	}

	public static CollageUtil getInstance() {
		if (instance == null) {
			synchronized (CollageUtil.class) {
				if (instance == null) {
					instance = new CollageUtil();
				}
			}
		}
		return instance;
	}

	public String uploadDocument(String caseType, String pdfLocation) {
		if (StringUtils.isBlank(caseType) || StringUtils.isBlank(pdfLocation)) {
			LOGGER.warn("Parameters are not valid caseType=[{}] pdfLocation=[{}]", caseType, pdfLocation);
			return null;
		}
		String jsonRequest = prepareRequest(caseType);
		if (StringUtils.isNotBlank(jsonRequest)) {
			File file = new File(pdfLocation);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("document", file);
			builder.addTextBody("meta-data", jsonRequest, ContentType.APPLICATION_JSON);
			HttpEntity entity = builder.build();

			HttpPut httpPUT = new HttpPut(SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_UPLOAD_URL));
			httpPUT.addHeader(Constants.COLLAGE_HEADER_AUTHENTICATE,
					SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_AUTHENTICATE));
			httpPUT.addHeader(Constants.COLLAGE_HEADER_AUTHORIZATION,
					SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_AUTHORIZE));
			httpPUT.setEntity(entity);
			return executePutRequest(httpPUT);
		}
		return null;
	}

	private String executePutRequest(HttpPut httpPUT) {
		String docId = null;
		LOGGER.info("Begin executing request...");
		HttpClient client = getHttpClient();
		try {
			HttpResponse response = client.execute(httpPUT);
			Writer outputWriter = new StringBuilderWriter();
			IOUtils.copy(response.getEntity().getContent(), outputWriter, Charset.defaultCharset());
			LOGGER.debug("Document uploaded reponse => [{}]", outputWriter.toString());
			@SuppressWarnings("unchecked")
			HashMap<String, String> result = new ObjectMapper().readValue(outputWriter.toString(), HashMap.class);
			docId = result.get(Constants.COLLAGE_RESPONSE_DOC_ID);
		} catch (Exception e) {
			LOGGER.error("Exception occured while uploading document => ", e);
		}
		pooledHttpClient.returnObject(client);
		LOGGER.info("End executing request");
		return docId;
	}

	private String prepareRequest(String caseType) {
		Map<String, String> reqBody = new HashMap<String, String>();
		reqBody.put("caseType", caseType);
		reqBody.put("mimeType", "application/pdf");
		String jsonRequest = serializedToJson(reqBody);
		return jsonRequest;
	}

	public String serializedToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		PrettyPrinter printer = new DefaultPrettyPrinter();
		mapper.writer(printer);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error("Exception occured while serializing object to jason.", e);
		}
		return json;
	}

	public InputStream getDocument(String docId, String caseType) {
		if (StringUtils.isBlank(docId) || StringUtils.isBlank(caseType)) {
			LOGGER.warn("Invalid parameters, doc id [{}] case type [{}]", docId, caseType);
			return null;
		}
		InputStream result = collageSecureClient.getDocInputStream(getEnvironment(),
				SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_AUTHENTICATE),
				SpringBeanUtil.getInstance().getProperty(Constants.COLLAGE_AUTHORIZE), caseType, docId, collageSession);
		if (result == null) {
			LOGGER.warn("Doc ID [{}] and case type [{}] did not return a valid document.", docId, caseType);
		}
		return result;
	}

	private class HttpClientFactory extends BasePooledObjectFactory<HttpClient> {
		private HttpClientBuilder httpClientBuilder;

		public HttpClientFactory() {
			super();
			// Using HTTP header authentication
			httpClientBuilder = HttpClientBuilder.create();
		}

		@Override
		public HttpClient create() throws Exception {
			return createHttpClient();
		}

		@Override
		public PooledObject<HttpClient> wrap(HttpClient obj) {
			return new DefaultPooledObject<HttpClient>(obj);
		}

		private HttpClient createHttpClient() {
			LOGGER.info("Creating HTTPClient instance");
			HttpClient client = httpClientBuilder.build();
			return client;
		}
	}
}
