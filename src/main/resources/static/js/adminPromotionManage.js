$(document).ready(function() {

    getAllMovies();

    getActivities();

    getVipList();

    getAllmembers();

    getAllcoupons();

    IsRoot();

    function IsRoot() {
        if(sessionStorage.getItem('id')==8){
            $("#manage-worker").show();
        }
    }

    function getActivities() {
        getRequest(
            '/activity/get',
            function (res) {
                var activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            var movieDomStr = "";
            if(activity.movieList.length){
                activity.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>"+movie.name+"</li>";
                });
            }else{
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            activitiesDomStr+=
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.name+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>优惠券："+activity.coupon.name+"</span>" +
                "        <span class='title'>满"+activity.coupon.targetAmount+"减<span class='error-text title'>"+activity.coupon.discountAmount+"</span></span>" +
                "        <span class='gray-text'>"+activity.coupon.description+"</span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(activitiesDomStr);
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    $("#activity-form-btn").click(function () {
       var form = {
           name: $("#activity-name-input").val(),
           description: $("#activity-description-input").val(),
           startTime: $("#activity-start-date-input").val(),
           endTime: $("#activity-end-date-input").val(),
           movieList: [...selectedMovieIds],
           couponForm: {
               description: $("#coupon-name-input").val(),
               name: $("#coupon-description-input").val(),
               targetAmount: $("#coupon-target-input").val(),
               discountAmount: $("#coupon-discount-input").val(),
               startTime: $("#activity-start-date-input").val(),
               endTime: $("#activity-end-date-input").val()
           }
       };

        postRequest(
            '/activity/publish',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#activityModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }

    function getAllmembers() {
        getRequest(
            '/vip/getVIPPay',
            function (res) {
                var memberList = res.content;
                $('#discount-member-input').append("<option value="+ -1 +">所有会员</option>");
                memberList.forEach(function (member) {
                    $('#discount-member-input').append("<option value="+ member.vipId +">"+member.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    var selectedMemberIds = new Set();
    var selectedMemberNames = new Set();

    $('#discount-member-input').change(function () {
        var memberId = $('#discount-member-input').val();
        var memberName = $('#discount-member-input').children('option:selected').text();
        if(memberId==-1){
            selectedMemberIds.clear();
            selectedMemberNames.clear();
        } else {
            selectedMemberIds.add(memberId);
            selectedMemberNames.add(memberName);
        }
        renderSelectedMembers();
    });

    //渲染选择的参加活动的会员
    function renderSelectedMembers() {
        $('#selected-member').empty();
        var membersDomStr = "";
        selectedMemberNames.forEach(function (memberName) {
            membersDomStr += "<span class='label label-primary'>"+memberName+"</span>";
        });
        $('#selected-member').append(membersDomStr);
    }

    function getAllcoupons() {
        getRequest(
            '/coupon/all',
            function (res) {
                var couponList = res.content;
                $('#discount-coupon-input').append("<option value="+ -1 +">所有优惠券</option>");
                couponList.forEach(function (coupons) {
                    $('#discount-coupon-input').append("<option value="+ coupons.id +">"+coupons.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    var selectedCouponIds = new Set();
    var selectedCouponNames = new Set();

    $('#discount-coupon-input').change(function () {
        var couponId = $('#discount-coupon-input').val();
        var couponName = $('#discount-coupon-input').children('option:selected').text();
        if(couponId==-1){
            selectedCouponIds.clear();
            selectedCouponNames.clear();
        } else {
            selectedCouponIds.add(couponId);
            selectedCouponNames.add(couponName);
        }
        renderSelectedCoupons();
    });

    //渲染选择赠送的优惠券
    function renderSelectedCoupons() {
        $('#selected-coupon').empty();
        var couponsDomStr = "";
        selectedCouponNames.forEach(function (couponName) {
            couponsDomStr += "<span class='label label-primary'>"+couponName+"</span>";
        });
        $('#selected-coupon').append(couponsDomStr);
    }

    $("#discount-form-btn").click( function () {
        var couponid=[...selectedCouponIds];
        var memberid=[...selectedMemberIds];
        postRequest(
            '/coupon/insert?userid='+memberid+'&couponid='+couponid,
            null,
            function (res){
                if (res.success){
                    alert("赠送成功！");
                    $("#discountModal").modal('hide');
                }
            },
            function(error){
                alert(error);
            }
        )
    });

    function getVipList() {
        getRequest(
            '/vip/getVIPPay',
            function (res) {
                $('.vip-user').empty();
                var vipStr =" ";
                res.content.forEach(function (v) {
                    vipStr +=
                        "<tr>"+
                        "<td>" + v.name + "</td>"+
                        "<td>" + v.sum+ "</td>"+
                        "</tr>";
                });
                $('.vip-user').append(vipStr);
            },
            function (error) {
                alert(error);
            });
    }
});