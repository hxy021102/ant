
var loading = true, currPage = 1, rows = 10;
var searchCategoryId = null;
$(function(){
    CAMEL_HOME.init();
});
var CAMEL_HOME = {
    init : function() {
        this._bindEvent();
        this._getItemCategory();
        this._initShops();
        $(document.body).infinite().on("infinite", function() {
            if(loading) return;
            loading = true;
            setTimeout(function() {
                CAMEL_HOME._getItemList();
            }, 200);
        });
        CAMEL_HOME._getItemList();

    },
    _bindEvent : function() {
        $('.ui-index-header-con').click(function(){
            //window.location.href = '../home/search.html';
            $('body').css('overflow', 'hidden');
            $('.ui-search-mask, .ui-search').show();
            $("#searchInp").focus();

            var searchValue = $.trim($("#searchInp").val()),searchHistory = CAMEL_HOME.getSearchHistory();
            if(Util.checkEmpty(searchValue) && searchHistory.length != 0) {
                $('.searchContent .txt').html('最近搜索');
                $('.searchContent .delete').show();
                $('.searchList').empty();
                for(var i in searchHistory) {
                    $('.searchList').append('<div class="item his">'+searchHistory[i]+'</div>');
                }
            }
        });
        $('#searchCancel').click(function(){
            $('body').css('overflow', 'auto');
            $('.ui-search-mask, .ui-search').hide();
            $('.searchContent .txt, .searchList').empty();
            $("#searchInp").val('');

        });

        $("#searchInp").bind("input propertychange", function() {
            var searchValue = $.trim($(this).val()),searchHistory = CAMEL_HOME.getSearchHistory();
            if(Util.checkEmpty(searchValue)) {
                if(searchHistory.length == 0) {
                    $('.searchContent .txt, .searchList').empty();
                    $('.searchContent .delete').hide();
                } else {
                    $('.searchContent .txt').html('最近搜索');
                    $('.searchContent .delete').show();
                    $('.searchList').empty();
                    for(var i in searchHistory) {
                        $('.searchList').append('<div class="item his">'+searchHistory[i]+'</div>');
                    }
                }

                return;
            }

            ajaxPost('api/apiItemController/getList', {name : searchValue}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.length == 0) {
                        if(searchHistory.length == 0) {
                            $('.searchContent .txt, .searchList').empty();
                            $('.searchContent .delete').hide();
                        } else {
                            $('.searchContent .txt').html('最近搜索');
                            $('.searchContent .delete').show();
                            $('.searchList').empty();
                            for(var i in searchHistory) {
                                $('.searchList').append('<div class="item his">'+searchHistory[i]+'</div>');
                            }
                        }
                    } else {
                        $('.searchContent .txt').html('搜索发现');
                        $('.searchContent .delete').hide();
                        $('.searchList').empty();
                        for(var i in result) {
                            var item = result[i];
                            $('.searchList').append('<div class="item" code="'+item.id+'">'+item.name+'</div>');
                        }
                    }

                }
            });
        });

        $('.searchList').on('click', '.item', function(){
            //$('.searchContent .txt, .searchList').empty();
            //$("#searchInp").val('');
            if($(this).hasClass('his')) {
                $("#searchInp").val($(this).html());
                CAMEL_HOME.search();
            } else {
                CAMEL_HOME.setSearchHistory($(this).html());
                window.location.href = '../item/item_detail.html?itemId=' + $(this).attr('code');
            }

        });
        $('.searchContent .delete').bind('click', function(){
            if(window.localStorage) {
                window.localStorage.removeItem('searchHistory');
                $('.searchContent .txt, .searchList').empty();
                $('.searchContent .delete').hide();
            }
        });
    },
    _initShops : function() {
        ajaxPost('api/apiUserController/getShops', {}, function(data){
            if(data.success && data.obj) {
                var shops = data.obj, items = [], initShop;

                for(var i in shops) {
                    var main = shops[i].parentId == -1 ? ' --主店' : '';
                    var item = {
                        title : shops[i].name+'-'+shops[i].address + main,
                        value : shops[i].id
                    };
                    items.push(item);

                    if(i == 0) initShop = shops[i];
                }
                if($.cookie('cur_shop_id')) {
                    $('#shopName').html($.cookie('cur_shop_name'));
                } else {
                    $('#shopName').html(initShop.name+'-'+initShop.address);
                    $.cookie('cur_shop_id', initShop.id, {path:'/'});
                    $.cookie('cur_shop_name', initShop.name+'-'+initShop.address, {path:'/'});
                }

                $('#shopName').select({
                    title: "切换门店",
                    items: items,
                    onChange:function(obj){
                        $.cookie('cur_shop_id', obj.values, {path:'/'});
                        $.cookie('cur_shop_name', obj.titles, {path:'/'});
                        $('#shopName').html(obj.titles);

                        currPage = 1;
                        CAMEL_HOME._getItemList();
                    }
                });
            } else {
                $('#shopName').css('color', 'red').html('门店未认证');
                $('#shopName').bind('click', function(){
                    $.modal({
                        title: "系统提示！",
                        text: "门店未认证或认证失败",
                        buttons: [
                            { text: "取消", className: "default" },
                            { text: "去认证", onClick: function(){
                                window.location.href = '../ucenter/authentication.html';
                            } }
                        ]
                    });
                });
            }
        });
    },
    _getItemCategory : function() {
        ajaxPost('api/apiItemCategoryController/dataGrid', {page:1, rows:50, sort:'seq', order:'asc'}, function(data){
            if(data.success) {
                var result = data.obj;
                for(var i in result.rows) {
                    var itemCategory = result.rows[i];
                    CAMEL_HOME._buildItemCategory(itemCategory);
                }
                setTimeout(function(){
                    $("#itemCategory").swiper({
                        slidesPerView: 4
                    });
                }, 50);
            }
        });
    },
    _getItemList : function() {
        //params = $.extend(params || {}, {page:currPage, rows:rows});
        var params = {page:currPage, rows:rows};
        if(searchCategoryId) params.categoryId = searchCategoryId;
        var shopId = $.cookie('cur_shop_id');
        if(shopId) params.shopId = shopId;
        if(currPage == 1) {
            $("#itemList").empty();
            $.showLoadMore();
        }
        ajaxPost('api/apiItemController/dataGrid', params, function(data){
            if(data.success) {
                var result = data.obj;
                if(result.rows.length != 0) {
                    for (var i in result.rows) {
                        var item = result.rows[i];
                        CAMEL_HOME._buildItem(item);
                    }
                } else {
                    if(result.total == 0)
                        $("#itemList").append(Util.noDate(2, '没有相关的商品'));
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
    _buildItemCategory : function(itemCategory) {
        var viewData = Util.cloneJson(itemCategory);
        var dom = Util.cloneDom("itemCategory_template", itemCategory, viewData);
        $("#itemCategory .swiper-wrapper").append(dom);
        dom.click(itemCategory.id, function(event){
            currPage = 1;
            searchCategoryId = event.data;
            CAMEL_HOME._getItemList();
        });
        return dom;
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

        dom.find('div.ui-btn').click(function(){
            window.event.stopPropagation();
        });
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

        dom.click(item.id, function(event){
            window.location.href = '../item/item_detail.html?itemId=' + event.data;
        });
        return dom;
    },
    search : function() {
        var q = $.trim($("#searchInp").val());
        if(Util.checkEmpty(q)) {
            $("#searchInp").focus();
            return;
        }
        CAMEL_HOME.setSearchHistory(q);
        //$('.searchContent .txt, .searchList').empty();
        //$("#searchInp").val('');
        window.location.href = '../home/search.html?q=' + q;
    },
    setSearchHistory : function(value) {
        if(window.localStorage) {
            var searchHistory = window.localStorage.searchHistory;
            if(searchHistory)
                searchHistory = JSON.parse(searchHistory);
            else
                searchHistory = [];
            Util.arrayRemove(searchHistory, value);
            if(searchHistory.length == 10) searchHistory.pop();
            searchHistory.unshift(value);
            window.localStorage.searchHistory = JSON.stringify(searchHistory);
        }
    },
    getSearchHistory : function() {
        if(window.localStorage) {
            var searchHistory = window.localStorage.searchHistory;
            if(!searchHistory) return [];
            return JSON.parse(searchHistory);
        }
    }
};

