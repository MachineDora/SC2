$(document).ready(function () {

    getOrderFormDetail();

    getRefundMessage();

    $('#refund-confirm').click(function () {
        getRequest(
            '/order/refund/' + window.location.href.split('=')[1],

            function (res) {
                if(res.success){
                    alert(res.content);
                }else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    })



    function getOrderFormDetail() {
        getRequest(
            '/order/get/detail/' + window.location.href.split('=')[1],

            function (res) {
                renderOrderFormDetail(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空

    function renderOrderFormDetail(orderForm) {
        $('.under-line').empty();
        $('.movie-on-list').empty();
        var couponFare=0;
        if(parseInt(orderForm.couponId)==0){
            couponFare = 0;
        }
        else{
            couponFare = parseInt(orderForm.coupon.discountAmount);
        }
        var underMessage = "";
        var uorderFormStr = "";
        var moddalBody="";
        var state = "";
        var movieName = orderForm.movieName;
        var schedule = orderForm.scheduleItem;
        var totalfare = parseInt(orderForm.ticketNumber)*parseInt(schedule.fare);
        orderForm.tickets.forEach(function (ticket) {
            var columnIndex = ticket.columnIndex+1;
            var rowIndex = ticket.rowIndex+1;
            if(ticket.state=="1"){
                state = "已完成"
            }else if(ticket.state=="0"){
                state = "未完成"
            }else if(ticket.state=="3"){
                state = "已退款"
            }else{
                state = "已失效"
            }
            uorderFormStr +=
                "<li class='movie-item card'>" +
                "<div class='movie-info'>" +
                "<div class='movie-title' style='margin-bottom: 10px'>" +
                "<span class='primary-text' style='font-size: 19px'>" + movieName + "</span>" +
                "<span class='label'>" + state+ "</span>" +
                "</div>" +
                "<div>座位：" + "<span style='font-size: 16px;color:#1caf9a'>"+rowIndex+"</span>座"+"<span style='font-size: 16px;color:#1caf9a'>"+columnIndex+"</span>排" + "</div>" +
                "<div style='display: flex'>" +
                "<span>开场时间" + JSON.stringify(schedule.startTime).substring(1,11)+" "+JSON.stringify(schedule.startTime).substring(12,20) + "</span>" +
                "<span style='margin-left: 30px '>结束时间" + "<span style='font-size: 16px;color:#1caf9a'>"+JSON.stringify(schedule.endTime).substring(1,11)+" "+JSON.stringify(schedule.endTime).substring(12,20) + "</span>"+"</span>" +
                "<span style='margin-left: 30px;'>票价：" +"<span style='font-size: 16px;color:#1caf9a'>"+ schedule.fare + "</span></span>" +
                "</li>";


        });$('.movie-on-list').append(uorderFormStr);
        underMessage +=
            "<span class='total-fare' style='margin-left: 40px;margin-right: 40px'>总价：" + "<span style='font-size: 16px;color:#1caf9a'>"+JSON.stringify(totalfare) + "</span></span>" +
            "<span class='coupon-fare' style='margin-left: 40px;margin-right: 40px'>优惠券减免：" + "<span style='font-size: 16px;color:#1caf9a'>"+JSON.stringify(couponFare) + "</span></span>" +
            "<span class='pay-fare' style='margin-left: 40px;margin-right: 40px'>付款：" + "<span style='font-size: 16px;color:#1caf9a'>"+JSON.stringify(totalfare-couponFare) + "</span></span>" +
            "<button type=\"button\" class=\"btn btn-primary\" data-backdrop=\"static\" data-toggle=\"modal\" data-target=\"#confirmRefundModal\" style='right: 40px;" +
            "position: absolute;top:100%'> 退票</button>";
        $('.under-line').append(underMessage);

    }
    function getRefundMessage() {
        getRequest(
            '/refund/get',
            function (res) {
                renderRefund(res.content);
            },
            function (error) {
                alert(error);
            }
        );
        function renderRefund(refund) {
            $('.refund-message').empty();
            var refundStr = '';
            refundStr +=
                "<div class='refund-container' >" +
                "    <div class='refund-ccc' style='margin-top: 15px;margin-bottom: 15px'>" +
                "       <div class='activity-line' style='margin-top: 10px;margin-left: 10px'>" +
                "           <span style='font-size:15px'>在</span><span style='font-size:15px'>电影开场前<span style='font-size:15px;color:#1caf9a'>"+refund.timeOne+"</span>小时之前退款</span>" +
                "           <span style='font-size:15px'>可以退付款金额的<span style='font-size:15px;color:#1caf9a'>"+parseInt(parseFloat(refund.timeOnePercent)*100)+"%</span></span>"+

                "       </div>" +
                "       <div class='activity-line'style='margin-top: 20px;margin-left: 10px'>" +
                "           <span style='font-size:15px'>在</span><span style='font-size:15px'>电影开场前<span style='font-size:15px;color:#1caf9a'>"+refund.timeTwo+"</span>小时之前退款</span>" +
                "           <span style='font-size:15px'>可以退付款金额的<span style='font-size:15px;color:#1caf9a'>"+parseInt(parseFloat(refund.timeTwoPercent)*100)+"%</span></span>"+
                "       </div>" +

                "       <div class='activity-line'style='margin-top: 20px;margin-left: 10px'>" +
                "           <span style='font-size:15px'>在</span><span style='font-size:15px'>电影开场前<span style='font-size:15px;color:#1caf9a'>"+refund.deadline+"</span>小时之前退款</span>" +
                "           <span style='font-size:15px'>可以退付款金额的<span style='font-size:15px;color:#1caf9a'>"+parseInt(parseFloat(refund.deadlinePercent)*100)+"%</span></span>"+
                "       </div>" +

                "       <div class='activity-line'style='margin-top: 20px;margin-left: 10px'>" +
                "           <span style='font-size:15px'></span><span style='font-size:15px'>距离电影开场<span style='font-size:15px;color:#1caf9a'>"+refund.deadline+"</span>小时以内不能退款</span>" +
                "       </div>" +
                "    </div>"+
                "</div>";
            $('.refund-message').append(refundStr);
        }
    }
});
