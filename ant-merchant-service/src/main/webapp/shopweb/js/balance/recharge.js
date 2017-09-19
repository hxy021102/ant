/**
 * Created by snow on 17/5/3.
 */
var type = GetRequest('type') || 1;
$(function () {
    $.hideLoadMore();

    $('.recharge-btn').click(function(){
        var amount = $("#amount").val();

        if(Util.checkEmpty(amount) || amount == 0) {
            $("#amount").val('').focus();
            return;
        }
        window.location.href = '../pay/recharge_pay.html?amount=' + amount*1000/10 + '&type=' + type;
    });
});
