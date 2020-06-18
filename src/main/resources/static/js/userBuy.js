$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
        $('.user-tickets').empty();
        var uticketStr = '';
        list.forEach(function (tws) {
            var columnIndex = tws.columnIndex+1;
            var rowIndex = tws.rowIndex+1;
            var zuowei = rowIndex+"排"+columnIndex+"座";
            var state = '';
            if(tws.state=="1"){
                state = "已完成"
            }else if(tws.state=="0"){
                state = "未完成"
            }else{
                state = "已失效"
            }
            uticketStr +=
                "<tr>"+
                    "<td>" + tws.schedule.movieName + "</td>"+
                    "<td>" + tws.schedule.hallName + "</td>"+
                    "<td>" + zuowei + "</td>"+
                    "<td>" + JSON.stringify(tws.schedule.startTime).substring(1,11)+" "+JSON.stringify(tws.schedule.startTime).substring(12,20)  + "</td>"+
                    "<td>" + JSON.stringify(tws.schedule.endTime).substring(1,11)+" "+JSON.stringify(tws.schedule.endTime).substring(12,20) + "</td>"+
                    "<td>" + state + "</td>"+
                "</tr>";
        });
        $('.user-tickets').append(uticketStr);
    }

});