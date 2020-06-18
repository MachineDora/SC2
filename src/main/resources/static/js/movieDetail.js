$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;
    var status=0;
    idArray=[];
    idArray.push(movieId);
    var movie_name0=document.getElementById('movie-name-input');
    var movie_date0=document.getElementById('movie-date-input');
    var movie_img0=document.getElementById('movie-img-input');
    var movie_description0=document.getElementById('movie-description-input');
    var movie_type0=document.getElementById('movie-type-input');
    var movie_length0=document.getElementById('movie-length-input');
    var movie_country0=document.getElementById('movie-country-input');
    var movie_star0=document.getElementById('movie-star-input');
    var movie_writer0=document.getElementById('movie-writer-input');
    var movie_language0=document.getElementById('movie-language-input');
    var movie_director0=document.getElementById('movie-director-input');

    function setInitValue(){
        getRequest(
               '/movie/'+movieId + '/' + userId,
               function(res){
               var dat=res.content;
                movie_name0.value=dat.name;
                movie_date0.value=dat.startDate.slice(0,10)
                movie_img0.value=dat.posterUrl;
                movie_description0.value=dat.description;
                movie_type0.value=dat.type;
                movie_length0.value=dat.length;
                movie_country0.value=dat.country;
                movie_star0.value=dat.starring;
                movie_writer0.value=dat.screenWriter;
                movie_language0.value=dat.language;
                movie_director0.value=dat.director;
               },
               function (error) {
                  alert(error);
                  }
               )
        }

    getMovie();

    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

// admin界面才有
    $("#mod-btn").click(function(){
        setInitValue();
    });
    $("#modify-btn").click(function () {
            var formData = getMovieForm();
                    if(!validateMovieForm(formData)) {
                        alert('您的输入不符合规范或前三项有信息未填！')
                        return;
                    }
                    postRequest(
                        '/movie/update',
                        formData,
                        function (res) {
                            getMovieList();
                            alert('电影已修改完毕！');
                            window.location.href = "/admin/movie/manage";
                            $("#modifymovie").modal('hide');
                        },
                         function (error) {
                            alert(error);
                        });
        });
        $("#delete-btn").click(function () {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data0=res.content;
                status=data0.status;
                if(status==1){
                    alert('该电影已经下架！');
                    return;
                }
                var mess=confirm('确认要下架该电影吗？');
                if(mess==true){
                var list0=getOffMovieId();
                postRequest(
                    '/movie/off/batch',
                    list0,
                    function(res){
                        if(res.message=='有电影后续仍有排片或已有观众购票且未使用'){
                            alert(res.message);
                        }
                        else{
                            alert('该电影已成功下架！');
                            window.location.href = "/admin/movie/manage";
                            }
                    },
                    function (error) {
                        alert(error);
                     });
                    }
            }
            )
        });

    function getOffMovieId(){
            return{
               movieIdList:idArray
            };
    }

    function getMovieForm() {
            return {
                id:movieId,
                name: $('#movie-name-input').val(),
                startDate: $('#movie-date-input').val(),
                posterUrl: $('#movie-img-input').val(),
                description: $('#movie-description-input').val(),
                type: $('#movie-type-input').val(),
                length: $('#movie-length-input').val(),
                country: $('#movie-country-input').val(),
                starring: $('#movie-star-input').val(),
                director: $('#movie-director-input').val(),
                screenWriter: $('#movie-writer-input').val(),
                language: $('#movie-language-input').val()
            };
        }

    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }

    function getMovieList() {
        getRequest(
            '/movie/all',
            function (res) {
                renderMovieList(res.content);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function renderMovieList(list) {
        $('.movie-on-list').empty();
        var movieDomStr = '';
        list.forEach(function (movie) {
            movie.description = movie.description || '';
            movieDomStr +=
                "<li class='movie-item card'>" +
                "<img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
                "<div class='movie-info'>" +
                "<div class='movie-title'>" +
                "<span class='primary-text'>" + movie.name + "</span>" +
                "<span class='label "+(!movie.status ? 'primary-bg' : 'error-bg')+"'>" + (movie.status ? '已下架' : (new Date(movie.startDate)>=new Date()?'未上映':'热映中')) + "</span>" +
                "<span class='movie-want'><i class='icon-heart error-text'></i>" + (movie.likeCount || 0) + "人想看</span>" +
                "</div>" +
                "<div class='movie-description dark-text'><span>" + movie.description + "</span></div>" +
                "<div>类型：" + movie.type + "</div>" +
                "<div style='display: flex'><span>导演：" + movie.director + "</span><span style='margin-left: 30px;'>主演：" + movie.starring + "</span>" +
                "<div class='movie-operation'><a href='/admin/movieDetail?id="+ movie.id +"'>详情</a></div></div>" +
                "</div>"+
                "</li>";
        });
        $('.movie-on-list').append(movieDomStr);
    }
});