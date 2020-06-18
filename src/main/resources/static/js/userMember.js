$(document).ready(function () {
    getVIP();
    getCoupon();
    var vipInfoId=0;


var isBuyState = true;
var vipCardId;

function getVIP() {
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                // 是会员
                $("#member-card").css("visibility", "visible");
                $("#member-card").css("display", "");
                $("#nonmember-card").css("display", "none");

                vipCardId = res.content.id;
                vipInfoId = res.content.kind;
                $("#member-id").text(res.content.id);
                $("#member-kind").text(res.content.name);
                $("#member-description").text(res.content.description);
                $("#member-balance").text("¥" + res.content.balance.toFixed(2));
                $("#member-joinDate").text(res.content.joinDate.substring(0, 10));
            } else {
                // 非会员
                $("#member-card").css("display", "none");
                $("#nonmember-card").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });

    getRequest(
        '/vip/getallVIPInfo',
        function (res) {
            if (res.success) {
                var list=res.content;
                $(".infofo").empty();
                var VIPInfosDomStr = "";
                list.forEach(function (VIPInfo) {
                VIPInfosDomStr+=
                    "<div  class='info' >" +
                    "<div class='cardName'>"+ VIPInfo.name + "</div>"+
                    "<div class='price'><b id='member-buy-price'>" + VIPInfo.price + "</b>元/张</div>" +
                    "<div id='member-buy-description' class='description'>" +"充值优惠：" + VIPInfo.description + "。永久有效" + "</div>" +
                    "<button onclick='buyClick(this.dataset);' class='btn_buyaa' data-vipinfo='"+JSON.stringify(VIPInfo)+"' >立即购买</button>"+
                    "<div class='cardToBuyImage'></div>"+
                    "</div>";
                });
                $(".infofo").append(VIPInfosDomStr);
            } else {
                alert(res.content);
            }
        },
        function (error) {
            alert(error);
        });
}

buyClick=function(data) {
    var a=data.vipinfo;
    var b=JSON.parse(a);
    vipInfoId=b.id;
    clearForm();
    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "none");
    isBuyState = true;
}

chargeClick=function() {
    clearForm();
    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}

function clearForm() {
    $('#userMember-form input').val("");
    $('#userMember-form .form-group').removeClass("has-error");
    $('#userMember-form p').css("visibility", "hidden");
}

confirmCommit = function() {
    if (validateForm()) {
        if ($('#userMember-cardNum').val() === "123123123" && $('#userMember-cardPwd').val() === "123123") {
            if (isBuyState) {
                postRequest(
                    '/vip/add/'+ sessionStorage.getItem('id')+'/'+vipInfoId,
                    null,
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("购买会员卡成功");
                        getVIP();
                    },
                    function (error) {
                        alert(error);
                    });
            } else {
                postRequest(
                    '/vip/charge',
                    {vipId: vipCardId, amount: parseInt($('#userMember-amount').val())},
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("充值成功");
                        getVIP();
                    },
                    function (error) {
                        alert(error);
                    });
            }
        } else {
            alert("银行卡号或密码错误");
        }
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userMember-cardNum').val()) {
        isValidate = false;
        $('#userMember-cardNum').parent('.form-group').addClass('has-error');
        $('#userMember-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userMember-cardPwd').val()) {
        isValidate = false;
        $('#userMember-cardPwd').parent('.form-group').addClass('has-error');
        $('#userMember-cardPwd-error').css("visibility", "visible");
    }
    if (!isBuyState && (!$('#userMember-amount').val() || parseInt($('#userMember-amount').val()) <= 0)) {
        isValidate = false;
        $('#userMember-amount').parent('.form-group').addClass('has-error');
        $('#userMember-amount-error').css("visibility", "visible");
    }
    return isValidate;
}

function getCoupon() {
    getRequest(
        '/coupon/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                var couponList = res.content;
                var couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.name +
                        '</div>' +
                        '<div class="description">' +
                        coupon.description +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '减' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startTime) + ' ~ ' + formatDate(coupon.endTime) + '</div>' +
                        '</div></div></div></div>'
                }
                $('#coupon-list').html(couponListContent);

            }
        },
        function (error) {
            alert(error);
        });
}

function formatDate(date) {
    return date.substring(5, 10).replace("-", ".");
}

});