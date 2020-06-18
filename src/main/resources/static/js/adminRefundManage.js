$(document).ready(function(){

    getRefund();

    IsRoot();

    function IsRoot() {
        if(sessionStorage.getItem('id')==8){
            $("#manage-worker").show();
        }
    }

    $("#refund-form-bnt").click(function () {
        var formData = getRefundForm();
        postRequest(
            '/refund/add',
            formData,
            function () {
                getRefund();
                $("#refundModal").modal('hide');
            },
            function (error) {
                alert(error);
            }

        )



    });


    function getRefund() {
        getRequest(
            '/refund/get',
            function (res) {
                renderRefund(res.content);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function renderRefund(refund) {
        $('.refund-effective').empty();
        var refundStr = '';
        refundStr +=
            "<div class='refund-container' >" +
            "    <div class='refund-ccc' style='margin-top: 15px;margin-bottom: 15px'>" +
            "       <div class='activity-line' style='margin-top: 10px;margin-left: 10px'>" +
            "           <span style='font-size:20px'>第一时段：</span><span style='font-size:15px'>在电影开场前<span style='font-size:20px;color:#009dff'>"+refund.timeOne+"</span>小时退款</span>" +
            "       </div>" +
            "       <div class='activity-line' style='margin-top: 10px;margin-left: 10px'>" +
            "           <span style='font-size:20px'>退款金额：</span>" +
            "           <span style='font-size:15px'>付款金额的<span style='font-size:20px;color:#009dff'>"+parseInt(parseFloat(refund.timeOnePercent)*100)+"%</span></span>"+
            "       </div>" +
            "       <div class='activity-line'style='margin-top: 20px;margin-left: 10px'>" +
            "           <span style='font-size:20px'>第二时段：</span><span style='font-size:15px'>在电影开场前<span style='font-size:20px;color:#009dff'>"+refund.timeTwo+"</span>小时退款</span>" +
            "       </div>" +
            "       <div class='activity-line'style='margin-top: 10px;margin-left: 10px'>" +
            "           <span style='font-size:20px'>退款金额：</span>" +
            "           <span style='font-size:15px'>付款金额的<span style='font-size:20px;color:#009dff'>"+parseInt(parseFloat(refund.timeTwoPercent)*100)+"%</span></span>"+
            "       </div>" +
            "       <div class='activity-line'style='margin-top: 20px;margin-left: 10px'>" +
            "           <span style='font-size:20px'>第三时段：</span><span style='font-size:15px'>在电影开场前<span style='font-size:20px;color:#009dff'>"+refund.deadline+"</span>小时退款</span>" +
            "       </div>" +
            "       <div class='activity-line'style='margin-top: 10px;margin-left: 10px'>" +
            "           <span style='font-size:20px'>退款金额：</span>" +
            "           <span style='font-size:15px'>付款金额的<span style='font-size:20px;color:#009dff'>"+parseInt(parseFloat(refund.deadlinePercent)*100)+"%</span></span>"+
            "       </div>" +
            "    </div>"+
            "</div>";
        $('.refund-effective').append(refundStr);
    }
});
function getRefundForm() {
    return {
        timeOne: parseInt($('#time-one').val()),
        timeOnePercent: parseFloat(parseInt($('#time-one-percent').val().substring(0,$('#time-one-percent').val().length-1))*0.01),
        timeTwo: parseInt($('#time-two').val()),
        timeTwoPercent: parseFloat(parseInt($('#time-two-percent').val().substring(0,$('#time-two-percent').val().length-1))*0.01),
        deadline: parseInt($('#deadline').val()),
        deadlinePercent: parseFloat(parseInt($('#deadline-percent').val().substring(0,$('#deadline-percent').val().length-1))*0.01)
    }
}