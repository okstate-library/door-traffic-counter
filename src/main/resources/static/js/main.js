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
        startDate: '-1m',
        endDate: end,
        minView: 2, 
        pickTime: false, 
        language: 'pt-BR' 
    });
    
});




