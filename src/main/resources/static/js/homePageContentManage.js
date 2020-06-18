$(document).ready(function(){

    getScrollBar();

    IsRoot();

    function IsRoot() {
        if(sessionStorage.getItem('id')==8){
            $("#manage-worker").show();
        }
    }

    $("#home-page-form-bnt").click(function () {
        var formData = getScrollBarForm();
        postRequest(
            '/scroll/bar/add',
            formData,
            function () {
                getScrollBar();
                $("#homePageModal").modal('hide');
            },
            function (error) {
                alert(error);
            }

        )



    });


    function getScrollBar() {
        getRequest(
            '/scroll/bar/get',
            function (res) {
                renderHomePage(res.content);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function renderHomePage(scrollbarList) {
        $('.refund-effective').empty();
        var i = 1;
        var refundStr = '';
        scrollbarList.forEach(function (scrollbar) {
            refundStr +=
                "<div class='refund-container' style='margin-bottom: 40px'>" +
                "    <div class='refund-ccc' style='margin-top: 15px;margin-bottom: 15px'>" +
                "       <div class='activity-line' style='margin-top: 10px;margin-left: 10px'>" +
                "           <span style='font-size:20px'>滚动栏"+i+"图片地址：</span><a href='"+scrollbar.picture+"' target='_blank'><span style='font-size:20px;color:#009dff'>" + scrollbar.picture + "</span></a>" +
                "       </div>" +
                "       <div class='activity-line' style='margin-top: 10px;margin-left: 10px'>" +
                "           <span style='font-size:20px'>指向内容：</span>" +
                "           <a href="+scrollbar.toWeb+" target='_blank'><span style='font-size:20px;color:#009dff'>" + scrollbar.toWeb + "</span></a>" +
                "       </div>" +
                "    </div>" +
                "</div>";
            i = i + 1;
        })
        $('.refund-effective').append(refundStr);
    }
});
function getScrollBarForm() {
    return {
        picture: $('#picture').val(),
        toWeb: $('#picture-to-web').val()
    }
}