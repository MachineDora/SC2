$(document).ready(function() {

    IsRoot();

    function IsRoot() {
        if(sessionStorage.getItem('id')==8){
            $("#manage-worker").show();
        }
    }

    var canSeeDate = 0;
    var newid=0;
    var row=0;
    var column=0;
    var name='';
    var hallId;
    var modifyid=1;


    getCanSeeDayNum();
    getCinemaHalls();
    getNewHall();
    getHalls();


    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                halllist=halls;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = "";
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<div class='cinema-hall'>" +
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }



    $('#hall-select').change (function () {//选择影厅
        hallId=$(this).children('option:selected').val();//就是那个框中选择的对象，这个要拉
        modifyid=hallId;
    });

    function getHalls() {//影厅下拉框
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getNewHall() {
        getRequest(
            '/hall/id',
            function (res) {
                newid = res.content;
                name=newid+" 号厅";
                $("#new-hall-id").text(newid);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }


    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $('#hall-modify-btn').click(function () {
        $("#hall-modify-btn").hide();
        $("#hallname").show();
        $("#hallrow").show();
        $("#hallcolumn").show();
        $("#row-set-input").val(row);
        $("#row-set-input").show();
        $("#column-set-input").val(column);
        $("#column-set-input").show();
        $("#name-set-input").val(name);
        $("#name-set-input").show();
        $("#hall-confirm-btn").show();
        $("#hall-cancel-btn").show();
    });

    $('#hall-confirm-btn').click(function () {
        var rowNum = $("#row-set-input").val();
        var columnNum=$("#column-set-input").val();
        var hallname=$("#name-set-input").val();
        // 验证一下是否为空
        if(rowNum<1 ||columnNum<1){
            alert('排或列为零！');
            return;
        }
        else if(hallname==null ||hallname==''){
            alert('名称为空！');
            return;
        }
        postRequest(
            '/hall/set',
            {id:newid,name:hallname,row:rowNum,column:columnNum},
            function (res) {
                if(res.success){
                    name=hallname;
                    getNewHall();
                    $('#hall-select').empty();
                    getHalls();
                    getCinemaHalls();
                    $("#row-set-input").hide();
                    $("#column-set-input").hide();
                    $("#name-set-input").hide();
                    $("#hall-confirm-btn").hide();
                    $("#hall-cancel-btn").hide();
                    $("#hall-modify-btn").show();
                    $("#hallname").hide();
                    $("#hallrow").hide();
                    $("#hallcolumn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $('#hall-cancel-btn').click(function () {
        $("#row-set-input").hide();
        $("#column-set-input").hide();
        $("#name-set-input").hide();
        $("#hall-confirm-btn").hide();
        $("#hall-cancel-btn").hide();
        $("#hall-modify-btn").show();
        $("#hallname").hide();
        $("#hallrow").hide();
        $("#hallcolumn").hide();
    });

    $("#modify-btn").click(function () {
        var modifyname=$("#hall-select").val();
        $("#modify-btn").hide();
        $("#modify-delete-btn").hide();
        $("#modifyname").show();
        $("#modifyhall").show();
        $("#hall-select").show();
        $("#modifyrow").show();
        $("#modifycolumn").show();
        $("#modify-row-input").val(row);
        $("#modify-row-input").show();
        $("#modify-column-input").val(column);
        $("#modify-column-input").show();
        $("#modify-name-input").val(modifyname);
        $("#modify-name-input").show();
        $("#modify-confirm-btn").show();
        $("#modify-cancel-btn").show();
        $("#modify-delete-false").hide();
    });

    $("#modify-cancel-btn").click(function () {
        $("#modify-btn").show();
        $("#modify-delete-btn").show();
        $("#modify-row-input").hide();
        $("#modify-column-input").hide();
        $("#modify-name-input").hide();
        $("#modify-confirm-btn").hide();
        $("#modify-cancel-btn").hide();
        $("#modifyname").hide();
        $("#modifyhall").hide();
        $("#hall-select").hide();
        $("#modifyrow").hide();
        $("#modifycolumn").hide();
        $("#modify-delete-btn").hide();
        $("#modify-delete-false").show();

    });

    $("#modify-confirm-btn").click(function () {
        var modifyname=$("#hall-select").val();
        getRequest(
            '/hall/check?id='+modifyname,
            function (res) {
                if(res.content=="可"){
                    var rowNum = $("#modify-row-input").val();
                    var columnNum=$("#modify-column-input").val();
                    var hallname=$("#modify-name-input").val();
                    if(rowNum<1 ||columnNum<1){
                        alert('排或列为零！');
                        return;
                    }
                    else if(hallname==null ||hallname==''){
                        alert('名称为空！');
                        return;
                    }
                    postRequest(
                        '/hall/modify',
                        {id:modifyname,name:hallname,row:rowNum,column:columnNum},
                        function (res) {
                            if(res.success){
                                $('#hall-select').empty();
                                getHalls();
                                getCinemaHalls();
                                $("#modify-row-input").hide();
                                $("#modify-column-input").hide();
                                $("#modify-name-input").hide();
                                $("#modify-confirm-btn").hide();
                                $("#modify-cancel-btn").hide();
                                $("#modify-btn").show();
                                $("#modifyhall").hide();
                                $("#hall-select").hide();
                                $("#modifyrow").hide();
                                $("#modifycolumn").hide();
                                $("#modifyname").hide();
                                $("#modify-delete-false").show();
                                $("#modify-delete-btn").hide();
                            } else{
                                alert(res.message);
                            }
                        },
                        function (error) {
                            alert(JSON.stringify(error));
                        }
                    );
                }
                else {
                    alert("该影厅有电影正在放映，不可修改！")
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#modify-delete-false").click(function () {
        $("#modifyhall").show();
        $("#hall-select").show();
        $("#modify-delete-false").hide();
        $("#modify-delete-btn").show();
        $("#modify-btn").hide();
        $("#modify-cancel").show();
    });

    $("#modify-cancel").click(function () {
        $("#modifyhall").hide();
        $("#hall-select").hide();
        $("#modify-delete-false").show();
        $("#modify-delete-btn").hide();
        $("#modify-btn").show();
        $("#modify-cancel").hide();
    });

    $("#modify-delete-btn").click(function () {
        var modifyname=$("#hall-select").val();
        getRequest(
            '/hall/check?id='+modifyname,
            function (res) {
                if(res.content==("可")){
                    postRequest(
                        '/hall/delete',
                        {id:0,name:modifyname,row:0,column:0},
                        function (res) {
                            if(res.success){
                                $('#hall-select').empty();
                                getHalls();
                                getCinemaHalls();
                                $("#modifyhall").hide();
                                $("#hall-select").hide();
                                $("#modify-delete-false").show();
                                $("#modify-delete-btn").hide();
                                $("#modify-btn").show();
                                $("#modify-cancel").hide();
                                alert("已删除");
                            } else{
                                alert(res.message);
                            }
                        },
                        function (error) {
                            alert(JSON.stringify(error));
                        }
                    );
                }
                else{
                    alert("该影厅有电影正在放映，不可删除！");
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });
});