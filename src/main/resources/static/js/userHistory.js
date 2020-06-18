$(document).ready(function () {
    getHistoryList();

    function getHistoryList() {
        getRequest(
            '/vip/getChargeHistory/' + sessionStorage.getItem('id'),
            function (res) {
                if (res.content != null) {
                    renderHistoryList(res.content);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function renderHistoryList(list) {
        $('.user-history').empty();
        var uhistoryStr = '';
        list.forEach(function (vch) {
            uhistoryStr +=
                "<tr>"+
                    "<td>" + vch.time + "</td>"+
                    "<td>" + vch.amount + "</td>"+
                "</tr>";
        });
        $('.user-history').append(uhistoryStr);
    }

});