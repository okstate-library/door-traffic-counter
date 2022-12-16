(function($) {
	$.toggleShowPassword = function(options) {
		var settings = $.extend({
			field : "#password",
			control : "#toggle_show_password",
		}, options);

		var control = $(settings.control);
		var field = $(settings.field);

		control.bind('click', function() {
			if (control.is(':checked')) {
				field.attr('type', 'text');
			} else {
				field.attr('type', 'password');
			}
		})
	};

}(jQuery));

$(document).ready(function() {

	$.toggleShowPassword({
		field : '#password',
		control : "#showPassword"
	});

	var end = new Date();

	$('#datetimepicker4').datetimepicker({
		format : 'yyyy-mm-dd',
		autoclose : true,
		startDate : new Date('2020-07-06'),
		endDate : end,
		minView : 2,
		pickTime : false,
		language : 'pt-BR'
	});

	$('#datetimepicker_start_date').datetimepicker({
		format : 'yyyy-mm-dd',
		autoclose : true,
		startDate : new Date('2020-07-06'),
		endDate : end,
		minView : 2,
		pickTime : false,
		language : 'pt-BR'
	});

	$('#datetimepicker_end_date').datetimepicker({
		format : 'yyyy-mm-dd',
		autoclose : true,
		startDate : new Date('2020-07-06'),
		endDate : end,
		minView : 2,
		pickTime : false,
		language : 'pt-BR'
	});

	$('#helpButton').click(function() {
		$("#exampleModal").modal();
	});

});

function callBrowseRequest() {

	var sDate = $("#datetimepicker_start_date").find("input").val();
	var eDate = $("#datetimepicker_end_date").find("input").val();

	if (Date.parse(sDate) > Date.parse(eDate)) {
		alert("Start date shouldn't greater than End date");

		return false;
		return;
	}

	$.ajax({
		type : "GET",
		cache : false,
		url : '/headcount/report',
		data : {
			"start_date" : $("#datetimepicker_start_date").find("input").val(),
			"end_date" : $("#datetimepicker_end_date").find("input").val()

		},
		beforeSend : function() {
			$('#loader').removeClass('hidden') // Loader
		},
		success : function(data) {

			console.log(data);

			$('#browseDataTable').dataTable().fnDestroy();

			var table = $('#browseDataTable').DataTable(
					{
						retrieve : true,
						dom : 'Bfrtip',
						buttons : [ 'excelHtml5' ],
						data : data,
						columns : [

						{
							"data" : "process_date",
							title : "Date"
						}, {
							"data" : "inoutcount.0",
							title : "Hr 1"
						}, {
							"data" : "inoutcount.1",
							title : "Hr 2"
						}, {
							"data" : "inoutcount.2",
							title : "Hr 3"
						}, {
							"data" : "inoutcount.3",
							title : "Hr 4"
						}, {
							"data" : "inoutcount.4",
							title : "Hr 5"
						}, {
							"data" : "inoutcount.5",
							title : "Hr 6"
						}, {
							"data" : "inoutcount.6",
							title : "Hr 7"
						}, {
							"data" : "inoutcount.7",
							title : "Hr 8"
						}, {
							"data" : "inoutcount.8",
							title : "Hr 9"
						}, {
							"data" : "inoutcount.9",
							title : "Hr 10"
						}, {
							"data" : "inoutcount.10",
							title : "Hr 11"
						}, {
							"data" : "inoutcount.11",
							title : "Hr 12"
						}, {
							"data" : "inoutcount.12",
							title : "Hr 13"
						}, {
							"data" : "inoutcount.13",
							title : "Hr 14"
						}, {
							"data" : "inoutcount.14",
							title : "Hr 15"
						}, {
							"data" : "inoutcount.15",
							title : "Hr 16"
						}, {
							"data" : "inoutcount.16",
							title : "Hr 17"
						}, {
							"data" : "inoutcount.17",
							title : "Hr 18"
						}, {
							"data" : "inoutcount.18",
							title : "Hr 19"
						}, {
							"data" : "inoutcount.19",
							title : "Hr 20"
						}, {
							"data" : "inoutcount.20",
							title : "Hr 21"
						}, {
							"data" : "inoutcount.21",
							title : "Hr 22"
						}, {
							"data" : "inoutcount.22",
							title : "Hr 23"
						}, {
							"data" : "inoutcount.23",
							title : "Hr 24"
						}, ],
						sort : [ 0, 'desc' ],
						"lengthMenu" : [ [ 20, 50, 100, 200, -1 ],
								[ 20, 50, 100, 200, "All" ] ],
						"pageLength" : 20,
						"select" : {
							style : 'multi'
						}
					});

			// $('#loader').addClass('hidden');

		},
		error : function() {
			// alert(error);
			alert('Error sec');
		},
		complete : function() { // Set our complete callback, adding the .hidden
			// class and hiding the spinner.
			$('#loader').addClass('hidden')
		},
	});
}
