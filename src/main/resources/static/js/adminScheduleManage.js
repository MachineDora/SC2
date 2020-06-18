var colors = [
    '#4fcaf9',
    '#ffabab',
    '#fff794',
    '#5cc3ff',
    '#FFCCCC',
    '#9966FF',
    'steelblue'
];

$(document).ready(function() {
    var hallId,
        scheduleDate = formatDate(new Date()),
        schedules = [];

    initSelectAndDate();//定义了个func，下面有解释

    IsRoot();

    function IsRoot() {
        if(sessionStorage.getItem('id')==8){
            $("#manage-worker").show();
        }
    }

    function getSchedules() {//获得排片信息，这是增加排片信息之后的事情

        getRequest(
            '/schedule/search?hallId='+hallId+'&startDate='+scheduleDate.replace(/-/g,'/'),
            function (res) {
                schedules = res.content;
                renderScheduleTable(schedules);
             },
            function (error) {
                alert(JSON.stringify(error));
             }
        );
    }

    function renderScheduleTable(schedules){//排片表的生成
        $('.schedule-date-container').empty();
        $(".schedule-time-line").siblings().remove();
        schedules.forEach(function (scheduleOfDate) {
            $('.schedule-date-container').append("<div class='schedule-date'>"+formatDate(new Date(scheduleOfDate.date))+"</div>");
            var scheduleDateDom = $(" <ul class='schedule-item-line'></ul>");
            $(".schedule-container").append(scheduleDateDom);
            scheduleOfDate.scheduleItemList.forEach(function (schedule,index) {
                var scheduleStyle = mapScheduleStyle(schedule);
                var scheduleItemDom =$(
                    "<li id='schedule-"+ schedule.id +"' class='schedule-item' data-schedule='"+JSON.stringify(schedule)+"' style='background:"+scheduleStyle.color+";top:"+scheduleStyle.top+";height:"+scheduleStyle.height+"'>"+
                    "<span>"+schedule.movieName+"</span>"+
                    "<span class='error-text'>¥"+schedule.fare+"</span>"+
                    "<span>"+formatTime(new Date(schedule.startTime))+"-"+formatTime(new Date(schedule.endTime))+"</span>"+
                    "</li>");
                scheduleDateDom.append(scheduleItemDom);
            });
        })
    }

    function mapScheduleStyle(schedule) {//这是那个排片表的颜色
        var start = new Date(schedule.startTime).getHours()+new Date(schedule.startTime).getMinutes()/60,
            end = new Date(schedule.endTime).getHours()+new Date(schedule.endTime).getMinutes()/60 ;
        return {
            color: colors[schedule.movieId%colors.length],
            top: 40*start+'px',//这两行是那个方块的大小
            height: 40*(end-start)+'px'
        }
    }

    function initSelectAndDate() {
        $('#schedule-date-input').val(scheduleDate);
        getCinemaHalls();
        getAllMovies();
        
        // 过滤条件变化后重新查询
        $('#hall-select').change (function () {//选择影厅
            hallId=$(this).children('option:selected').val();//就是那个框中选择的对象，这个要拉
            getSchedules();
        });
        $('#schedule-date-input').change(function () {//选择日期
            scheduleDate = $('#schedule-date-input').val();//这个要输入
            getSchedules();
        });
    }

    function getCinemaHalls() {//影厅下拉框
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getSchedules();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getAllMovies() {//电影名下拉框
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                movieList.forEach(function (movie) {
                    $('#schedule-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $("#schedule-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    $(document).on('click','.schedule-item',function (e) {//点排片信息
        var schedule = JSON.parse(e.target.dataset.schedule);
        $("#schedule-edit-hall-input").children('option[value='+schedule.hallId+']').attr('selected',true);
        $("#schedule-edit-movie-input").children('option[value='+schedule.movieId+']').attr('selected',true);
        $("#schedule-edit-start-date-input").val(schedule.startTime.slice(0,16));
        $("#schedule-edit-end-date-input").val(schedule.endTime.slice(0,16));
        $("#schedule-edit-price-input").val(schedule.fare);
        $('#scheduleEditModal').modal('show');
        $('#scheduleEditModal')[0].dataset.scheduleId = schedule.id;
        console.log(schedule);
    });
    
    $('#schedule-form-btn').click(function () {
        var form = {
            hallId: $("#schedule-hall-input").children('option:selected').val(),
            movieId : $("#schedule-movie-input").children('option:selected').val(),
            startTime: $("#schedule-start-date-input").val(),
            endTime: $("#schedule-end-date-input").val(),
            fare: $("#schedule-price-input").val()
        };
        //todo 需要做一下表单验证？
        var isValidata = true;
                if(!form.startTime) {
                    isValidata = false;
                    $('#schedule-start-date-input').parent('.form-group').addClass('has-error');
                }
                if(!form.endTime) {
                    isValidata = false;
                    $('#schedule-end-date-input').parent('.form-group').addClass('has-error');
                }
                if(!form.fare) {
                    isValidata = false;
                    $('#schedule-price-input').parent('.form-group').addClass('has-error');
                }
                if(isNaN(Number(form.fare))) {
                    isValidata = false;
                    $('#schedule-price-input').parent('.form-group').addClass('has-error');
                }
        if(isValidata==false){
            alert('最后一项应为数字或最后三项有内容未填！');
            return;
        }
        postRequest(//这不是表单验证，这是生成排片表的内容
            '/schedule/add',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $('#schedule-edit-form-btn').click(function () {
        var form = {
            id: Number($('#scheduleEditModal')[0].dataset.scheduleId),
            hallId: $("#schedule-edit-hall-input").children('option:selected').val(),
            movieId : $("#schedule-edit-movie-input").children('option:selected').val(),
            startTime: $("#schedule-edit-start-date-input").val(),
            endTime: $("#schedule-edit-end-date-input").val(),
            fare: $("#schedule-edit-price-input").val()
        };
        //todo 需要做一下表单验证？
        var isValidata = true;
                if(!form.startTime) {
                    isValidata = false;
                    $('#schedule-start-date-input').parent('.form-group').addClass('has-error');
                }
                if(!form.endTime) {
                    isValidata = false;
                    $('#schedule-end-date-input').parent('.form-group').addClass('has-error');
                }
                if(!form.fare) {
                    isValidata = false;
                    $('#schedule-price-input').parent('.form-group').addClass('has-error');
                }
                if(isNaN(Number(form.fare))) {
                    isValidata = false;
                    $('#schedule-price-input').parent('.form-group').addClass('has-error');
                }
        if(isValidata==false){
            alert('最后一项应为数字或最后三项有内容未填！');
            return;
        }
        postRequest(
            '/schedule/update',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该排片信息吗")
        if (r) {
            deleteRequest(
                '/schedule/delete/batch',
                {scheduleIdList:[Number($('#scheduleEditModal')[0].dataset.scheduleId)]},
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
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

