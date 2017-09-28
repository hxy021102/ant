
var loading = true, currPage = 1, rows = 10;
var q = GetRequest('q'), shopId = $.cookie('cur_shop_id');
$(function(){
    CAMEL_SEARCH.init();
});
var CAMEL_SEARCH = {
    init : function() {
        document.title = q;
        $('.header-name').html(q);
        this._bindEvent();
        
        $(document.body).infinite().on("infinite", function() {
            if(loading) return;
            loading = true;
            setTimeout(function() {
                CAMEL_SEARCH._getItemList();
            }, 200);
        });
        CAMEL_SEARCH._getItemList();

    },
    _bindEvent : function() {
        
    },
    _getItemList : function() {
        var params = {page:currPage, rows:rows, name: q};
        if(shopId) params.shopId = shopId;
        ajaxPost('api/apiItemController/dataGrid', params, function(data){
            if(data.success) {
                var result = data.obj;
                if(result.rows.length != 0) {
                    for (var i in result.rows) {
                        var item = result.rows[i];
                        CAMEL_SEARCH._buildItem(item);
                    }
                } else {
                    if(result.total == 0)
                        $("#itemList").append(Util.noDate(2, '没有搜索到相关的商品'));
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
                $(document.body).destroyInfinite();
                $.hideLoadMore();
            }
        });
    },
    _buildItem : function(item) {
        var viewData = Util.cloneJson(item);
        viewData.marketPrice = '￥' + Util.fenToYuan(item.marketPrice);
        viewData.contractPrice = '￥' + Util.fenToYuan(item.contractPrice);
        var dom = Util.cloneDom("item_list_template", item, viewData);
        dom.find('i.ui-img-cover').css('background-image', 'url('+item.url+')');
        dom.addClass('ui-row-flex');
        if(!item.contractPrice) {
            dom.find('.bot').html('<p>'+viewData.marketPrice+'</p>');
        }
        $("#itemList").append(dom);
        // 加入购入车
        dom.find('a.carts').click(item, function(event){
            var $li = $(this).closest('li'), num = parseInt($li.find('.quantity').val());
            if(event.data.quantity < num) {
                $.toast("库存不足", "forbidden");
                return;
            }
            ajaxPost('api/apiShoppingController/add', {itemId : event.data.id, quantity : num}, function(data){
                if(data.success) {
                    $.toast("加入购物车成功", 1500);
                    initShoppingNum();
                }
            });
        });
        // 立即抢购
        /*dom.find('a.btn').click(item, function(event){
            if(event.data.quantity <= 0) {
                $.toast("库存不足", "forbidden");
                return;
            }
            window.location.href = '../order/order_confirm.html?itemId=' + event.data.id;
        });*/
        dom.find('.quantity').blur(item.quantity, function(event){
            var num = $(this).val();
            if(!num || num == 0) {
                $.toast("<font size='3pt;'>数量超出范围~</font>", "text");
                $(this).val(1);
            }
            if(num > event.data) {
                $.toast("<font size='3pt;'>数量超出范围~</font>", "text");
                $(this).val(event.data);
            }
        });

        dom.find('.sub').click(function(){
            var $li = $(this).closest('li'), num = parseInt($li.find('.quantity').val());
            if(num <= 1) return;
            $li.find('.quantity').val(num - 1);
        });

        dom.find('.add').click(item.quantity, function(event){
            var $li = $(this).closest('li'), num = parseInt($li.find('.quantity').val());
            if(num == event.data) {
                $.toast("<font size='3pt;'>亲，不能购买更多哦</font>", "text");
                return;
            }
            $li.find('.quantity').val(num + 1);
        });

        dom.find('i.ui-img-cover').click(item.id, function(event){
            window.location.href = '../item/item_detail.html?itemId=' + event.data;
        });
        return dom;
    }
};

