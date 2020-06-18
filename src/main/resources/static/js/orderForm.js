$(document).ready(function () {



    getOrderList();

    function getOrderList() {
        getRequest(
            '/order/get/' + sessionStorage.getItem('id'),

            function (res) {
                renderOrderFormList(res.content);
            },
            function (error) {
                alert(error);
            });
    }



    // TODO:填空
    function renderOrderFormList(list) {
        $('.movie-on-list').empty();
        var uorderFormStr = "";
        var state = "";
        list.forEach(function (of) {
            if(of.state=="0"){
                state = "已完成"
            }else{
                state = "已退款"
            }
            uorderFormStr +=
                "<li class='movie-item card' style='position: relative;'>" +
                "<div class='movie-info'>" +
                "<div class='movie-title'>" +
                "<span class='primary-text'>" + of.movieName + "</span>" +
                "<span class='label "+(!of.state ? 'primary-bg' : 'error-bg')+"'>" + state+ "</span>" +
                "</div>" +
                "<div>票数：" + of.ticketNumber + "</div>" +
                "<div style='display: flex'><span>日期：" + JSON.stringify(of.time).substring(1,11)+" "+JSON.stringify(of.time).substring(12,20) + "</span>" +
                "<div class='movie-operation'  style='margin-right: 30px'><a href='/user/orderFormDetail?id="+ of.orderformId +"' style='right: 45px;" +
                "position: absolute;'>查看详情</a></div></div>" +
                "</div>"+
                "</li>";


         });$('.movie-on-list').append(uorderFormStr);


    }


});