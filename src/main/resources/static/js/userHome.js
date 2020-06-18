$(document).ready(function(){

    getRandomMoveList();
    scrollbar();
    getScrollBar();
    getBoxOfficeList();
    getLikeDataList();

    function scrollbar() {
        var timer = null;
        var num = 0;
        var move;
        var myFn = function () {
            num++;
            if (num > 5) {
                num = 0;
            }
            var move = -800 * num;
            $('.banner ul').stop().animate({'left': move + 'px'}, 500);
            $('.banner .b_order li').eq(num).addClass('current').siblings().removeClass('current');
        };

        //定时器
        timer = setInterval(myFn, 4000);

        //hover事件 of .banner
        $('.banner').hover(function () {
            clearInterval(timer);
        }, function () {
            timer = setInterval(myFn, 4000);
        })

        //click事件 of ol li
        $('.b_order li').click(function () {
            //clearInterval(timer);
            $(this).addClass('current').siblings().removeClass('current');
            num = $(this).index();
            move = -800 * num;
            $('.banner .b_pic').stop().animate({'left': move + 'px'}, 500);
        })

        //click事件 of btn_r
        $('.btn_r').click(function () {
            myFn();
        })

        //click事件 of btn_l
        $('.btn_l').click(function () {
            num--;
            if (num < 0) {
                num = 5;
            }
            move = -800 * num;
            $('.b_pic').stop().animate({'left': move + 'px'}, 500);//切记animate不要写成animation
            $('.b_order li').eq(num).addClass('current').siblings().removeClass('current');
        })
    }
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
        $('.b_pic').empty();
        var refundStr = '';
        scrollbarList.forEach(function (scrollbar) {
            refundStr +=
                "<li><a href=" +scrollbar.toWeb+ "><img src ="+scrollbar.picture+ "width ='800' height ='330'></a></li>"
        })
        $('.b_pic').append(refundStr);
    }

    function getRandomMoveList(){//随机获得四部正在热映的电影
            getRequest(
                    '/movie/all/exclude/off',
                    function (res) {
                        var movieList=res.content;
                        $('.hot-table').empty();
                        var movieDomStr = '';
                        var random=new Array(5);
                        var item=movieList;
                        var index=0;
                        for(i = 0;i < random.length;i++){
                            index=Math.floor(Math.random()*(item.length));
                            random[i]=item[index];
                            item.splice(index,1);
                        }
                        random.forEach(function (movie) {
                            movie.description = movie.description || '';
                            movieDomStr +=
                                "<li class='movie-item2 cardM'>" +
                                "<a href='/user/movieDetail?id="+ movie.id +"'><img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/></a>" +
                                "<div class='movie-title'>" +
                                "<a href='/user/movieDetail?id="+ movie.id +"'>" + movie.name + "</a>" +
                                "</div>" +
                                "</li>";
                            });
                        $('.hot-table').append(movieDomStr);
                        },
                     function (error) {
                    alert(error);
                });
    }

    function getBoxOfficeList() {
        getRequest(
            '/movie/boxOffice',
            function (res) {
                renderBoxOfficeList(res.content);
            },
            function (error) {
                alert(error);
            });
    }


    function renderBoxOfficeList(list) {
        $('.table-item').empty();
        var boxOfficeStr = "";
        var index=1;
        var style='xu1';
        list.forEach(function (movie) {
            if(movie.boxOffice!=null){
                boxOfficeStr +=
                    "<li>" +
                    "<div class="+style+">" +
                    index+
                    "</div>" +
                    "<a href='/user/movieDetail?id="+
                    movie.movieId+
                    "'>" +
                    "<div class='xu3'>"+
                    movie.name+
                    "</div>"+
                    "</a>"+
                    "<div class='piaofang'>" +
                    movie.boxOffice+
                    "</div>"+
                    "</li>";
                index=index+1;
                if(index>3){
                    style='xu2';
                }
            }
        });
        $('.table-item').append(boxOfficeStr);
    }

    function getLikeDataList() {
        getRequest(
            '/movie/like',
            function (res) {
                renderLikeDataList(res.content);
            },
            function (error) {
                alert(error);
            });
    }


    function renderLikeDataList(list) {
        $('.like-item').empty();
        var likeDataStr = "";
        var index=1;
        var style='xu1';
        list.forEach(function (like) {
            likeDataStr +=
                "<li>" +
                "<div class="+style+">" +
                index+
                "</div>" +
                "<a href='/user/movieDetail?id="+
                like.movieId+
                "'>" +
                "<div class='xu3'>"+
                like.name+
                "</div>"+
                "</a>"+
                "<div class='like-data'>" +
                like.sum+
                "</div>"+
                "</li>";
            index=index+1;
            if(index>3){
                style='xu2';
            }
        });
        $('.like-item').append(likeDataStr);
    }

})
