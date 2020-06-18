$(document).ready(function(){

    getMovieList('');
     var type="全部类型";
     var state="2";


    function getMovieList(keyword) {
        getRequest(
            '/movie/search?keyword='+keyword,
            function (res) {
                renderMovieList(res.content);
            },
             function (error) {
            alert(error);
        });
    }

    function renderMovieList(list) {
        $('.movie-on-list').empty();
        var movieDomStr = '';
        list.forEach(function (movie) {
            movie.description = movie.description || '';
            movieDomStr +=
                "<li class='movie-item cardM'>" +
                "<a href='/user/movieDetail?id="+ movie.id +"'><img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/></a>" +
                "<div class='movie-info'>" +
                "<div class='movie-title'>" +
                "<span class='primary-text'><a href='/user/movieDetail?id="+ movie.id +"'>" + movie.name + "</a></span>" +
                "</div>" +
                "</div>"+
                "</li>";
        });
        $('.movie-on-list').append(movieDomStr);
    }

    $('#search-btn').click(function () {
        getMovieList($('#search-input').val());
    })

    $(document).on('click','.keykey',function (e) {
        var a=e.currentTarget.dataset.key;
        alert(a);
    })

    $('.new-filler-container a').click(function () {
            var temp=this.id;
            type = temp;
            selectMovies();
        });

        $('.state-filler-container a').click(function () {
            var temp=this.id;
            state = temp;
            selectMovies();
        });

        function selectMovies(){
            getRequest(
                '/movie/type/'+type+'/'+state,
                function (res) {
                    if(res.content==null){
                        renderMovieList(res.content);
                    }
                    else{
                        renderMovieList(res.content);
                    }
                },
                function (error) {
                    alert(error);
                });
        }

        $(".new-filler-container a").click(function(){
            $(".new-filler-container a").removeClass("active");
            $(this).addClass("active");
        });

        $(".state-filler-container a").click(function(){
            $(".state-filler-container a").removeClass("active");
            $(this).addClass("active");
        });


});