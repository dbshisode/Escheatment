// global variable for the layout-instance object
var docsLayout;
var docWin;

/********************************************
 * implement "contains" case-insensitively
 * use this code for jQuery 1.7 and before
 ********************************************/
jQuery.expr[':'].Contains = function(a, i, m) {
  return jQuery(a).text().toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
};

/********************************************
 * implement "contains" case-insensitively
 * use this code for jQuery 1.8 and after
 *
jQuery.expr[":"].Contains = jQuery.expr.createPseudo(function(arg) {
    return function( elem ) {
        return jQuery(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
    };
});
********************************************/

$(document).ready( function() {
    var docsOnlyRowsSize    = '*,0';
    var docsCalRowsSize     = '*,270';
    var dualPaneColsSize    = '400,*,0';
    var singlePaneColsSize  = '400,*,0';
    var northPaneSize = 34;                 // default north pane size
    
    $.fn.setFrameSize = function(frame, target, size){
        parent.document.getElementById(frame).setAttribute(target, size, 0);
    };    

    $.fn.loadCaseDocuments = function(){
        $('#loading').remove();
        $('#content-docs').empty();
        $('<div id="loading"><img src="/Escheatment/resources/css/escheatment/images/ajax-loader-indicator1.gif" /></div>').appendTo('#content-docs').show();
        $.get('roa-get-data', {rand: Math.random()}, function( resp ){
            $('#content-docs').html( resp );
            $('#loading').remove();

            // sort documents by filing date (5th column) in descending order (1), and document name (2nd column) in asc order
            //$("#doc").tablesorter({
                    //sortList: [[5,1], [2,0]]
            //});
			$("#doc").tablesorter();
			
            // paint doc icon columns based on panes state
            //var panesState = $.cookie('panesState');
            //$.fn.paintDocIconColumns(panesState);
            // highlight first row(s) of documents that will be initially displayed
            //if ( panesState == 'single-pane') {
                $('tr').eq(1).addClass('highlighted');
                $('#doc a.doc-link-icon-left:first').addClass('doc-selected');
            //} else {
                //$('#doc a.doc-link-icon-left:first').addClass('doc-selected');
                //$('#doc a.doc-link-icon-right').eq(1).addClass('doc-selected');
            //}
            
            // doc tooltips
            $('.doc-tooltip').tipsy({gravity: 's'});
            // sealed tooltip
            $('.doc-sealed').tipsy({gravity: 's'});
            // filer tooltips
            $('.filer-link').tipsy({gravity: 'e', html:true});
            
            // set default URLs of documents to be displayed in left and right panes
            var urlFirstDoc = "roa-right-frame";
            //var urlSecondDoc = "doc_frame.cfm";

            // set document filter state to 'ALL' [ option 1 ]            
            $("#docsShowMO").attr('checked', 'checked').button('refresh');
            $.cookie('docFilterState','show-minuteorders', {expires: 30, path: '/'});
            // show all docs
            $.fn.showMinuteOrders();
            // get URLs of docs to be initially displayed in the left and right panes
            urlFirstDoc =  $('a.doc-link-icon-left').eq(0).attr('href');
            //urlSecondDoc = $('a.doc-link-icon-left').eq(1).attr('href');

            // set state based on whether 'ALL' or 'Selected' button is pressed [ option 2 ]
            var docFilterState = $.cookie('docFilterState');
            
            if ( docFilterState == 'show-selected-docs') {
                $("#docsShowSelected").attr('checked', 'checked').button('refresh');
                // show selected docs only
                $.fn.showSelectedDocs();
                // get URLs of selected docs to be initially displayed in the left and right panes
                urlFirstDoc =  $('#content-docs #doc :checkbox:checked').parent().parent().find('a.doc-link-icon-left').eq(0).attr('href');                
            }
            else {
                $("#docsShowAll").attr('checked', 'checked').button('refresh');
                $.cookie('docFilterState','show-all-docs', {expires: 30, path: '/'});
                // show all docs
                $.fn.showAllDocs();
                // get URLs of docs to be initially displayed in the left and right panes
                //urlFirstDoc =  $('#doc a.doc-link-icon-left').eq(0).attr('href');
                //urlSecondDoc = $('#doc a.doc-link-icon-left').eq(1).attr('href');
                urlFirstDoc =  $('.doc-link-icon-left:first').attr('href');
            }
            
            // if no docs are present (selected or otherwise), display blank docs
            if ( typeof urlFirstDoc === "undefined" ) {
                urlFirstDoc = "roa-right-frame";
            }

            window.open( ( urlFirstDoc  + '&rand=' + Math.random() + '#toolbar=0' ), 'rightframe' );            
            return false;
        });
    };
	
    $.fn.loadFilteredDocuments = function(){
		var date1val = $("#date1").val();
		var date2val = $("#date2").val();
        $('#loading').remove();
        $('#content-docs').empty();
        $('<div id="loading"><img src="/Escheatment/resources/css/escheatment/images/ajax-loader-indicator1.gif" /></div>').appendTo('#content-docs').show();
        $.get('roa-get-data?date1=' + date1val + '&date2=' + date2val, {rand: Math.random()}, function( resp ){
            $('#content-docs').html( resp );
            $('#loading').remove();

            // sort documents by filing date (5th column) in descending order (1), and document name (2nd column) in asc order
            $("#doc").tablesorter({
                    sortList: [[5,1], [2,0]]
            });		
            
            // doc tooltips
            $('.doc-tooltip').tipsy({gravity: 's'});
            // sealed tooltip
            $('.doc-sealed').tipsy({gravity: 's'});
            // filer tooltips
            $('.filer-link').tipsy({gravity: 'e', html:true});
            
            // set default URLs of documents to be displayed in left and right panes
            var urlFirstDoc = "roa-right-frame";

            // get URLs of docs to be initially displayed in the left and right panes
            urlFirstDoc =  $('a.doc-link-icon-left').eq(0).attr('href');
			$("#docsShowAll").attr('checked', 'checked').button('refresh');
			$.cookie('docFilterState','show-all-docs', {expires: 30, path: '/'});
			// show all docs
			$.fn.showAllDocs();
			// get URLs of docs to be initially displayed in the left and right panes
			urlFirstDoc =  $('#doc a.doc-link-icon-left').eq(0).attr('href');
			
            return false;
        });
    };	

    $.fn.loadCaseParticipants = function(){
        $('#loading').remove();
        $('#content-participants').empty();
        $('<div id="loading"><img src="css/escheatment/images/ajax-loader-indicator1.gif" /></div>').appendTo('#content-participants').show();
        $.get('caseparticipants.do', {rand: Math.random()}, function( resp ){
            $('#content-participants').html( resp );
            $('#loading').remove();

            // address tooltips
            $('.address').tipsy({gravity: 's'});

            return false;
        });
    };

    $.fn.loadFutureEvents = function(){
        $('#loading').remove();
        $('#content-futureevents').empty();
        $('<div id="loading"><img src="css/escheatment/images/ajax-loader-indicator1.gif" /></div>').appendTo('#content-futureevents').show();
        $.get('casefutureevents.do', {rand: Math.random()}, function( resp ){
            $('#content-futureevents').html( resp );
            $('#loading').remove();
            return false;
        });
    };

    $.fn.loadProposedOrders = function(){
        $('#loading').remove();
        $('#content-proposedorders').empty();
        $('<div id="loading"><img src="css/escheatment/images/ajax-loader-indicator1.gif" /></div>').appendTo('#content-proposedorders').show();
        $.get('caseproposedorderdocs.do', {rand: Math.random()}, function( resp ){
            $('#content-proposedorders').html( resp );
            $('#loading').remove();

            // sort documents by filing date (6th column) in descending order (1), and document name (2nd column) in asc order
            $("#doc-po").tablesorter({
                    sortList: [[5,1], [2,0]]
            });

            // paint doc icon columns based on panes state
            var panesState = $.cookie('panesState');
            $.fn.paintDocIconColumns(panesState);
            
            // highlight first row(s) of documents that will be initially displayed
            if ( panesState == 'single-pane') {
                $('#doc-po a.doc-link-icon-left:first').addClass('doc-selected');
            } else {
                $('#doc-po a.doc-link-icon-left:first').addClass('doc-selected');
                //$('#doc-po a.doc-link-icon-right').eq(1).addClass('doc-selected');
            }

            // doc tooltips
            $('.doc-tooltip').tipsy({gravity: 's'});
            // sealed tooltip
            $('.doc-sealed').tipsy({gravity: 's'});
            // filer tooltips
            $('.filer-link').tipsy({gravity: 'e', html:true});

            // set default URLs of documents to be displayed in left and right panes
            var urlFirstDoc = "doc-blank.jsp";
            var urlSecondDoc = "doc-blank.jsp";

            // set state based on whether 'ALL' or 'Selected' button is pressed [ option 2 ]
            var docFilterState = $.cookie('docFilterState');
            if ( docFilterState == 'show-selected-docs') {
                $("#docsShowSelected").attr('checked', 'checked').button('refresh');
                // show selected docs only
                $.fn.showSelectedDocs();
                // get URLs of selected docs to be initially displayed in the left and right panes
                urlFirstDoc =  $('#content-docs #doc-po :checkbox:checked').parent().parent().find('a.doc-link-icon-left').eq(0).attr('href');
                urlSecondDoc = $('#content-docs #doc-po :checkbox:checked').parent().parent().find('a.doc-link-icon-left').eq(1).attr('href');
            }
            else {
                $("#docsShowAll").attr('checked', 'checked').button('refresh');
                $.cookie('docFilterState','show-all-docs', {expires: 30, path: '/'});
                // show all docs
                $.fn.showAllDocs();
                // get URLs of docs to be initially displayed in the left and right panes
                urlFirstDoc =  $('#doc-po a.doc-link-icon-left').eq(0).attr('href');
                urlSecondDoc = $('#doc-po a.doc-link-icon-left').eq(1).attr('href');
            }
            // alert( urlFirstDoc );

            // if no docs are present (selected or otherwise), display blank docs
            if ( typeof urlFirstDoc === "undefined" ) {
                urlFirstDoc = "doc-blank.jsp?d=1";
            }
            if ( typeof urlSecondDoc === "undefined" ) {
                urlSecondDoc = "doc-blank.jsp?d=2";
            }

            window.open( ( urlFirstDoc  + '#toolbar=0' ), 'elfdocframe1' );
            //window.open( ( urlSecondDoc + '#toolbar=0' ), 'elfdocframe2' );
            return false;
        });
    };
   
    $.fn.loadLegalWorkups = function(){
        $('#loading').remove();
        $('#content-legalworkups').empty();
        $('<div id="loading"><img src="css/escheatment/images/ajax-loader-indicator1.gif" /></div>').appendTo('#content-legalworkups').show();
        $.get('caselegalworkups.do', {rand: Math.random()}, function( resp ){
            $('#content-legalworkups').html( resp );
            $('#loading').remove();
            return false;
        });
    };

    $.fn.showMinuteOrders = function(){
        var contentState = $.cookie('contentState');
        if ( contentState == 'proposedorders' ) {
            $('#doc-po tr').hide();
            $('a:Contains("Minute")','#doc-po').closest('tr').show();
        } else {
            $('#doc tr').hide();
            $('a:Contains("Minute")','#doc').closest('tr').show();
			$('#header').show();
        }
    };
	
    $.fn.showPublicDocs = function(){
		$('#doc tr').hide();
		//$('a:Contains("Minute")','#doc').closest('tr').show();
		$('td:nth-child(7):Contains("1")','#doc').closest('tr').show();
		$('#header').show();
    };	

    $.fn.showSelectedDocs = function(){
        var contentState = $.cookie('contentState');
        if ( contentState == 'proposedorders' ) {
            $('#doc-po tr').show();
            $('#content-proposedorders :checkbox:not(:checked)').closest('tr').hide();
        } else {
            $('#doc tr').show();
            $('#content-docs :checkbox:not(:checked)').closest('tr').hide();
        }
       
        /* TEMP for workups deployment
            $('#doc tr').show();
            $('#content-docs :checkbox:not(:checked)').closest('tr').hide();
        end TEMP */
    };

    $.fn.showAllDocs = function(){
        var contentState = $.cookie('contentState');
        if ( contentState == 'proposedorders' ) {
            $('#doc-po tr').show();
        } else {
            $('#doc tr').show();
        }
    };

    $.fn.paintDocIconColumns = function( panesState ){
        var contentState = $.cookie('contentState');
        if ( panesState == 'single-pane') {
            if ( contentState == 'proposedorders' ) {
                // extend doc name column since icons are not there any more
                $('#doc-po td:nth-child(3)').removeClass('doc-name').addClass('doc-name-long');
                // hide docs icon columns
                $('#doc-po td:nth-child(2), #doc-po th:nth-child(2)').hide();
            } else {
                // extend doc name column since icons are not there any more
                $('#doc td:nth-child(3)').removeClass('doc-name').addClass('doc-name-long');
                // hide docs icon columns
                $('#doc td:nth-child(2), #doc th:nth-child(2)').hide();
            }
        } else {
            if ( contentState == 'proposedorders' ) {
                // shrink doc name column so icons can fit
                $('#doc-po td:nth-child(3)').removeClass('doc-name-long').addClass('doc-name');
                // show doc icon columns
                $('#doc-po td:nth-child(1), #doc-po th:nth-child(1)').show();
                $('#doc-po td:nth-child(2), #doc-po th:nth-child(2)').show();
            } else {
                // shrink doc name column so icons can fit
                $('#doc td:nth-child(3)').removeClass('doc-name-long').addClass('doc-name');
                // show doc icon columns
                $('#doc td:nth-child(1), #doc th:nth-child(1)').show();
                $('#doc td:nth-child(2), #doc th:nth-child(2)').show();
            }
        }
    };

    // keyword search
    $('#keyword').keyup(function (e) {
        var keyword = $(this).val();
        //alert( keyword );
        if ( ! keyword ) {
            var docFilterState = $.cookie('docFilterState');
            if ( docFilterState == 'show-selected-docs') { // if 'selected' button is on, go back to showing all selected docs
                $.fn.showSelectedDocs();
            } else { // otherwise, show all docs
                $.fn.showAllDocs();
            }
        } else {
            /*
            $('#doc tbody').hide();
            $('a:Contains("' + keyword + '")','#doc').closest('tbody').show();  // look for keyword within document name link
            $('td:Contains("' + keyword + '")','#doc').closest('tbody').show(); // look for keyword within all other table cells
            */
            $('#doc tr').hide();			
            $('td:nth-child(2):Contains("' + keyword + '")','#doc').closest('tr').show();         // look for keyword within document name link
            $('td:nth-child(5):Contains("' + keyword + '")','#doc').closest('tr').show();   // look for keyword within date column
            $('td:nth-child(6):Contains("' + keyword + '")','#doc').closest('tr').show();   // look for keyword within filer (hidden column)
			$('#header').show();
            //$('td:Contains("' + keyword + '")','#doc').closest('tr').show();              // any td within table
            //$('.filer-link[title*="' + keyword + '"]','#doc').closest('tr').show();       // title field
        }
    });

    /* toolbar button switch content
    $('#toolbar-button-switchcontent').click( function() {
        var contentState = $.cookie('contentState');
        if ( contentState == 'participants') {
            $('#toolbar-button-switchcontent').removeClass('toolbar-button-content-alt');
            $.cookie('contentState','docs');
            $('#content-participants').hide();
            $.fn.loadCaseDocuments();
            $('#content-docs').show();
        } else {
            $('#toolbar-button-switchcontent').addClass('toolbar-button-content-alt');
            $.cookie('contentState','participants');
            $('#content-docs').hide();
            $.fn.loadCaseParticipants();
            $('#content-participants').show();
        }
    });
    */

    // case toolbar: case docs button
    $('#button-casedocs').click( function() {

        //$('#toolbar-button-switchcontent').removeClass('toolbar-button-content-alt');
        $.cookie('contentState','docs');
        $('#content-participants').hide();
        $('#content-proposedorders').hide();
        $('#content-legalworkups').hide();
        $('#content-futureevents').hide();
        $.fn.loadCaseDocuments();
        $('#content-docs').show();
    });

    // case toolbar: participants button
    $('#button-participants').click( function() {
        //$('#toolbar-button-switchcontent').addClass('toolbar-button-content-alt');
        $.cookie('contentState','participants');
        $('#content-docs').hide();
        $('#content-proposedorders').hide();
        $('#content-legalworkups').hide();
        $('#content-futureevents').hide();
        $.fn.loadCaseParticipants();
        $('#content-participants').show();
    });

    /* case toolbar: proposed orders button */
    $('#button-orders').click( function() {
        $.cookie('contentState','proposedorders');
        $('#content-docs').hide();
        $('#content-participants').hide();
        $('#content-legalworkups').hide();
        $('#content-futureevents').hide();
        $.fn.loadProposedOrders();
        $('#content-proposedorders').show();
    });

    // case toolbar: legal workups button
    $('#button-workups').click( function() {
        $.cookie('contentState','legalworkups');
        $('#content-docs').hide();
        $('#content-participants').hide();
        $('#content-proposedorders').hide();
        $('#content-futureevents').hide();
        $.fn.loadLegalWorkups();
        $('#content-legalworkups').show();
    });

    // case toolbar: future events button
    $('#button-futureevents').click( function() {
        $.cookie('contentState','futureevents');
        $('#content-docs').hide();
        $('#content-participants').hide();
        $('#content-legalworkups').hide();
        $('#content-proposedorders').hide();
        $.fn.loadFutureEvents();
        $('#content-futureevents').show();
    });
	
    // case toolbar: date filter button
    $('#button-datefilter').click( function() {
        $.fn.loadFilteredDocuments();
    });	
	
    // case toolbar: date filter clear button
    $('#button-clear-datefilter').click( function() {
        $.fn.loadCaseDocuments();
		$("#date1").val("");
		$("#date2").val("");
    });		

    // toolbar button switch view
    $('#toolbar-button-switchview').click( function() {
        var viewState = $.cookie('viewState');
        if ( viewState == 'single-view') {
            $('#toolbar-button-switchview').removeClass('toolbar-button-view-alt');
            $.cookie('viewState','dual-view', {expires: 30, path: '/'});
            $.fn.setFrameSize('leftFrameset','rows', docsCalRowsSize);
        } else {
            $('#toolbar-button-switchview').addClass('toolbar-button-view-alt');
            $.cookie('viewState','single-view', {expires: 30, path: '/'});
            $.fn.setFrameSize('leftFrameset','rows', docsOnlyRowsSize);
        }
    });

    // toolbar button switch panes
    $('#toolbar-button-switchpane').click( function() {
        var panesState = $.cookie('panesState');
        if ( panesState == 'dual-pane') {
            $('#toolbar-button-switchpane').removeClass('toolbar-button-pane-alt');
            $.cookie('panesState','single-pane', {expires: 30, path: '/'});
            $.fn.setFrameSize('mainFrameset','cols', singlePaneColsSize);
            $.fn.paintDocIconColumns('single-pane');
        } else {
            $('#toolbar-button-switchpane').addClass('toolbar-button-pane-alt');
            //$.cookie('panesState','dual-pane', {expires: 30, path: '/'});
            $.fn.setFrameSize('mainFrameset', 'cols', dualPaneColsSize);
			$.fn.paintDocIconColumns('dual-pane');
        }
    });

    // toolbar button merge documents
    $('#toolbar-button-merge').click( function(event) {
      event.preventDefault();
      event.stopPropagation();
      // choose between targeting all or just selected documents
      var target = 'all';
      var docFilterState = $.cookie('docFilterState');
      if ( docFilterState == 'show-selected-docs') {
          target = 'selected';
      }

      var windowWidth = 980;
      var windowHeight = 720;
      //window.open( ( this.href + '?target=' + target ), 'MergeDocuments' );
      window.open( ( this.href ), 'MergeDocuments', 'width=' + windowWidth + ', height=' + windowHeight + ', left=40, top=40, toolbar=no, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=yes, modal=no');
      return false;
    });

    // document icon left
    $('#content-docs').on('click', 'a.doc-link-icon-left', function(event) {
      $('a.doc-link-icon-left').removeClass('doc-selected');
      $(this).addClass('doc-selected');
      $('tr').removeClass('highlighted');
      //alert('clicked');
      //alert( this.href + '&rand=' + Math.random() + '#toolbar=0' );
      event.preventDefault();
      event.stopPropagation();
      //window.open( this.href, 'elfdocframe1' );
      window.open( ( this.href + '#toolbar=0' ), 'rightframe' );
      return false;
    });


    // document name links
    $('#content-docs').on('click', 'a.doc-link', function(event) {
      //$('a.doc-link-icon-left').removeClass('doc-selected');
      $('tr').removeClass('highlighted');
      $(this).parent().parent().addClass('highlighted');
      //alert( this.href );
      event.preventDefault();
      event.stopPropagation();

      var windowWidth = screen.width - 425;
      var windowHeight = screen.height - 80;
      docWin = window.open( this.href,'cmsdoc','width=' + windowWidth + ', height=' + windowHeight + ', left=410, top=0, toolbar=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, modal=no');
      return false;
    });

    // select document
    $('#content-docs').on("click", function() {
        $.get('selectdocument.do', {'id': $(this).val(),'state': $(this).attr('checked') , 'r': Math.random()}, function( returned ){
            if ( returned.indexOf("success") == -1) { // a selection has NOT been saved
                alert( returned );
            }
            // if this is a 'show selected' view, hide rows with unchecked boxes
            var docFilterState = $.cookie('docFilterState');
            if ( docFilterState == 'show-selected-docs') {
                $.fn.showSelectedDocs();
            }
            else if ( docFilterState === "docsShowMO" ) { // if 'show minute orders' view
                $.fn.showMinuteOrders();
            }
        });
    });
	

    // select document ( proposed orders )
    $('#content-proposedorders').on("click", "input[name$='checkboxDocPrep']", function() {
        //alert ( 'index = ' + $(this).val() + ' state = ' + $(this).attr('checked') );
        $.get('proposedorderselectdocument.do', {'id': $(this).val(),'state': $(this).attr('checked') , 'r': Math.random()}, function( returned ){
            if ( returned.indexOf("success") == -1) { // a selection has NOT been saved
                alert( returned );
            }
            // if this is a 'show selected' view, hide rows with unchecked boxes
            var docFilterState = $.cookie('docFilterState');
            if ( docFilterState == 'show-selected-docs') {
                $.fn.showSelectedDocs();
            }
            else if ( docFilterState === "docsShowMO" ) { // if 'show minute orders' view
                $.fn.showMinuteOrders();
            }
        });
    });

    // footer toolbar - 'show all' / 'show selected' / 'show minute orders'
    $( "#footer-toolbar-filters" ).buttonset()
    .change(function() {
        var radioButtonChecked = $("input[name='docfilter']:checked")[0];
        var buttonId = $(radioButtonChecked).attr('id');
        // alert( buttonId );
        if ( buttonId === "docsShowSelected" ) { // show selected docs only
            $.cookie('docFilterState','show-selected-docs', {expires: 30, path: '/'});
            $.fn.showSelectedDocs();
        }
        else if ( buttonId === "docsShowMO" ) {
            $.cookie('docFilterState','show-minuteorders', {expires: 30, path: '/'});
            $.fn.showMinuteOrders();
        }
        else if ( buttonId === "docsShowPublic" ) {
            $.cookie('docFilterState','show-public', {expires: 30, path: '/'});
            $.fn.showPublicDocs();
        }		
        else { // show all docs
            $.cookie('docFilterState','show-all-docs', {expires: 30, path: '/'});
            $.fn.showAllDocs();
        }
    });

    // finalized case button
    $( "#finalized" ).button({
        icons: {primary:'ui-icon-circle-minus'}, // ui-icon-closethick
        //icons: { primary: this.checked ? 'ui-icon-check' : 'ui-icon-closethick' },
        text: false,
        create: function( event, ui ) {
            if ( this.checked ) {
                $(this).button("option", { 
                    icons: {primary: 'ui-icon-check'}
                });
            }
        }
    }).change(function() {
        // toggle icon
        $(this).button("option", {
            icons: {primary: this.checked ? 'ui-icon-check' : 'ui-icon-circle-minus'}
        });
        // save
        $.get('finalize.do', {'checked': this.checked , 'r': Math.random()}, function( returned ){
            if ( returned.indexOf("success") == -1) { // a selection has NOT been saved
                alert( returned );
            }            
        });
    });

    // finalized proposed order button
    $( "#finalizedPo" ).button({
        icons: {primary:'ui-icon-circle-minus'},
        //icons: { primary: this.checked ? 'ui-icon-check' : 'ui-icon-closethick' },
        text: false,
        create: function( event, ui ) {
            if ( this.checked ) {
                $(this).button("option", {
                    icons: {primary: 'ui-icon-check'}
                });
            }
        }
    }).change(function() {
        // toggle icon
        $(this).button("option", {
            icons: {primary: this.checked ? 'ui-icon-check' : 'ui-icon-circle-minus'}
        });
        // save
        $.get('proposedorderfinalize.do', {'checked': this.checked , 'r': Math.random()}, function( returned ){
            if ( returned.indexOf("success") == -1) { // a selection has NOT been saved
                alert( returned );
            }
        });
    });

    // tooltips
    $('.link-tooltip').tipsy({gravity: 'n'});

    $(".fg-button")
    .hover(
        function(){
            $(this).addClass("ui-state-hover");
        },
        function(){
            $(this).removeClass("ui-state-hover");
        }
    )
    .mousedown(function(){
        $(this).parents('.fg-buttonset-single:first').find(".fg-button.ui-state-active").removeClass("ui-state-active");
        if( $(this).is('.ui-state-active.fg-button-toggleable, .fg-buttonset-multi .ui-state-active') ){
            $(this).removeClass("ui-state-active");
        }
        else {
            $(this).addClass("ui-state-active");
            /* if calendar filter state is 'notheard' and user has clicked on 'continuted', 'off calendar' or 'completed' button hide this case
            var calFilterState = $.cookie('calendar-filter-state');
            if ( $(this).is('.state-continued, .state-offcalendar, .state-completed') && calFilterState === 'notheard' ){
                $(this).closest('tbody').hide();
            }
            */
        }
    })
    .mouseup(function(){
        if(! $(this).is('.fg-button-toggleable, .fg-buttonset-single .fg-button, .fg-buttonset-multi .fg-button') ){
            $(this).removeClass("ui-state-active");
        }
    });


    // set content state to 'show documents'
    //$('#toolbar-button-switchcontent').removeClass('toolbar-button-content-alt');

    /* display case docs or proposed order docs */
/*    var contentState = $.cookie('contentState');
    if ( contentState == 'proposedorders') {
        $('#content-participants').hide();
        $('#content-docs').hide();
        $('#content-legalworkups').hide();
        $('#content-futureevents').hide();
        $( '#button-orders' ).addClass( "ui-state-active" );
        $.fn.loadProposedOrders();
    } else {*/
        $.cookie('contentState','docs');
        $('#content-participants').hide();
        $('#content-proposedorders').hide();
        $('#content-legalworkups').hide();
        $('#content-futureevents').hide();
        $( '#button-casedocs' ).addClass( "ui-state-active" );
        $.fn.loadCaseDocuments();
    /*}*/

    /* TEMP for workups deployment
        $.cookie('contentState','docs');
        $('#content-participants').hide();
        $('#content-legalworkups').hide();
        $('#content-futureevents').hide();
        $( '#button-casedocs' ).addClass( "ui-state-active" );
        $.fn.loadCaseDocuments();
    end TEMP */

    // set view state
    var viewState = $.cookie('viewState');
    if ( viewState == 'single-view') {
        $('#toolbar-button-switchview').addClass('toolbar-button-view-alt');
        //$.fn.setFrameSize('leftFrameset', 'rows', docsOnlyRowsSize);
    } else {
        $('#toolbar-button-switchview').removeClass('toolbar-button-view-alt');
        $.cookie('viewState','dual-view', {expires: 30, path: '/'});
        //$.fn.setFrameSize('leftFrameset', 'rows', docsCalRowsSize);
    }

    // set panes state
    var panesState = $.cookie('panesState');
    if ( panesState == 'single-pane') {        
		$('#toolbar-button-switchpane').addClass('toolbar-button-pane-alt');
        //$.fn.setFrameSize('mainFrameset', 'cols', singlePaneColsSize);        
    }
    else {
        $('#toolbar-button-switchpane').removeClass('toolbar-button-pane-alt');
        //$.cookie('panesState','dual-pane', {expires: 30, path: '/'});
		//$.fn.setFrameSize('mainFrameset', 'cols', dualPaneColsSize);
    }
    

    /* set document filter state
    var docFilterState = $.cookie('docFilterState');
    if ( docFilterState == 'show-selected-docs') {
        $("#docsShowSelected").attr('checked', 'checked').button('refresh');
    } else {
        $("#docsShowAll").attr('checked', 'checked').button('refresh');
        $.cookie('docFilterState','show-all-docs', {expires: 30, path: '/'});
    }
    */
   
    // layout
    docsLayout = $('body').layout({
        north__resizable: false,
        north__size: northPaneSize
        // north__closable: false,
        // useStateCookie: true		
    });
});
