(function ($) {
    $.toggleShowPassword = function (options) {
        var settings = $.extend({
            field: "#password",
            control: "#toggle_show_password",
        }, options);

        var control = $(settings.control);
        var field = $(settings.field);

        control.bind('click', function () {
            if (control.is(':checked')) {
                field.attr('type', 'text');
            } else {
                field.attr('type', 'password');
            }
        })
    };

}(jQuery));

$(document).ready(function () {
   
    $.toggleShowPassword({
        field: '#password',
        control: "#showPassword"
    });
    
    var end =  new Date();
    
    $('#datetimepicker4').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        startDate: new Date('2020-07-06'),
        endDate: end,
        minView: 2, 
        pickTime: false, 
        language: 'pt-BR' 
    });
    
    $('#datetimepicker5').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        startDate: new Date('2020-07-06'),
        endDate: end,
        minView: 2, 
        pickTime: false, 
        language: 'pt-BR'
    });
    
    $('#helpButton').click(function(){
    	 $("#exampleModal").modal();
    }); 
    
});




