$(document).ready(function() {

    var idd=0;

    getVIPCards();

    IsRoot();

    function IsRoot() {
        if(sessionStorage.getItem('id')==8){
            $("#manage-worker").show();
        }
    }

    $("#member-form-btn").click(function () {
        var VIPform=getVIPform();
        postRequest(
                '/vip/addInfo',
                VIPform,
                function (res) {
                    getVIPCards();
                    $("#memberModal").modal('hide');
                },
                function (error) {
                   alert(error);
                });
    })


    function getVIPform() {
        return {
            name: $('#recharge-name-input').val(),
            price: $('#recharge-price-input').val(),
            description: $('#recharge-description-input').val(),
            money1: $('#recharge-target-input').val(),
            money2: $('#recharge-discount-input').val(),
        };
    }

    function getVIPCards() {
        getRequest(
            '/vip/getallVIPInfo',
            function (res) {
                var VIPInfos = res.content;
                renderVIPInfosList(VIPInfos);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    function renderVIPInfosList(List) {
        $(".content-member").empty();
        var VIPInfosDomStr = "";
        List.forEach(function (VIPInfo) {
            VIPInfosDomStr+=
                "<li id='schedule-"+ VIPInfo.id +"' class='member-container' data-vipinfo='"+JSON.stringify(VIPInfo)+"' >" +
                "    <div class='member-card card'>" +
                "       <div class='member-line'>" +
                "           <span class='title'>"+VIPInfo.name+"</span>" +
                "       </div>" +
                "    </div>" +
                "    <div class='member-coupon primary-bg'>" +
                "        <span class='new-title'>"+VIPInfo.price+"元每张"+"</span>" +
                "        <span class='new-title'>"+"充值"+VIPInfo.description+"</span>" +
                "    </div>" +
                "</li>";
            });
        $(".content-member").append(VIPInfosDomStr);
    }

    $(document).on('click','.member-container',function (e) {
            var a=e.currentTarget.dataset.vipinfo;
            var VIPInfo = JSON.parse(a);
            idd=VIPInfo.id;
            $("#recharge-name-edit").val(VIPInfo.name);
            $("#recharge-description-edit").val(VIPInfo.description);
            $("#recharge-price-edit").val(VIPInfo.price);
            $("#recharge-target-edit").val(VIPInfo.money1);
            $("#recharge-discount-edit").val(VIPInfo.money2);
            $('#CardModal').modal('show');
            });

    $('#member-edit-btn').click(function () {
        var form = {
            id:idd,
            name: $('#recharge-name-edit').val(),
            price: $('#recharge-price-edit').val(),
            description: $('#recharge-description-edit').val(),
            money1: $('#recharge-target-edit').val(),
            money2: $('#recharge-discount-edit').val(),

        }
        //这里表单验证非常重要！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        postRequest(
            '/vip/updateInfo',
            form,
            function (res) {
                if(res.success){
                    getVIPCards();
                    $("#CardModal").modal('hide');
                } else{
                    alert(res.message);//其实是这里非常重要，要检测有没有人已经买了这张卡
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })

   $('#member-edit-remove-btn').click(function () {
        var r=confirm("确认要删除该会员卡信息吗")
        if(r){
            deleteRequest(
                '/vip/deleteInfo',
                idd,
                function (res) {
                    if(res.success){
                        getVIPCards();
                        alert("该会员卡已删除");
                        $("#CardModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

});