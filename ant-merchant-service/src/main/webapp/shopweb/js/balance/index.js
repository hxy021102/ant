/**
 * Created by snow on 17/5/3.
 */
var loading = true, currPage = 1, rows = 10, balanceId;
var type = GetRequest('type') || 1;
$(function () {
    //$.hideLoadMore();
    if(type == 4) {
        document.title = '我的桶账余额';
        $('.header-name').html(document.title);
        $('.balance_content .wallet-header img').attr('src', '../images/mine-8.png');
    }
    init();

    $('.recharge').click(function(){
        window.location.href = '../balance/recharge.html?type=' + type;
    });
});

function init() {
    getBalance();

    $(document.body).infinite().on("infinite", function() {
        if(loading) return;
        loading = true;
        setTimeout(function() {
            getBalanceLoglist();
        }, 200);
    });
    getBalanceLoglist();
}

function getBalance() {
    ajaxPostSync('api/apiBalanceController/getBalance', {type : type}, function(data){
        if(data.success) {
            balanceId = data.obj.id;
            $('.balanceAmount').html('￥' + Util.fenToYuan(data.obj.amount));
        }
    });
}

function getBalanceLoglist() {
    ajaxPost('api/apiBalanceController/dataGrid', {page:currPage, rows:rows, balanceId:balanceId}, function(data){
        if(data.success) {
            var result = data.obj;
            if(result.rows.length != 0) {
                for(var i in result.rows) {
                    buildBalanceLog(result.rows[i]);
                }
            } else {
                if(result.total == 0)
                    $(".wallet-list").append(Util.noDate(1, '您还没有相关的记录'));
            }

            if(result.rows.length >= rows) {
                loading = false;
                currPage ++;
                $.showLoadMore();
            } else {
                loading = true;
                $.hideLoadMore();
            }
        } else {
            loading = true;
            $.hideLoadMore();
        }
    });
}

function buildBalanceLog(balanceLog) {
    var viewData = Util.cloneJson(balanceLog);
    viewData.addtime = new Date(balanceLog.addtime.replace(/-/g,"/")).format('yyyy-MM-dd HH:mm');
    var pre;
    var amount = balanceLog.amount;
    if(balanceLog.amount<0){
        pre = "-";
        amount = -amount;
    }else{
        pre = "+";
    }
    viewData.amount = pre + Util.fenToYuan(amount) + '元';

    var dom = Util.cloneDom("balance_log_template", balanceLog, viewData);
    dom.addClass('ui-row-flex');
    $('.wallet-list').append(dom);

    return dom;
}