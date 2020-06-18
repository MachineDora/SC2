$(document).ready(function(){

    getWorkerList();
    IsRoot();

    function IsRoot() {
        if(sessionStorage.getItem('id')==8){
            $("#manage-worker").show();
        }
    }

    $("#worker-form-btn").click(function () {
        var workername=$("#worker-name-input").val();
        var workerpassword=$("#worker-password-input").val();
        if(workername=='' || workerpassword==''){
            alert('用户名和密码不能为空！')
        }
        else {
            postRequest(
                '/creat?name='+workername+'&password='+workerpassword,
                null,
                function (res){
                    if (res.success){
                        getWorkerList();
                    }
                    else {
                        alert(res.message);
                    }
                },
                function(error){
                    alert(error);
                }
            )
        }
    });

    $("#worker-delete-btn").click(function () {
        var workername=$("#worker-name-input").val();
        postRequest(
            '/delete?name='+workername,
            null,
            function (res){
                if(res.message=="该账号不存在"){
                    alert(res.message);
                }
                else{
                    getWorkerList();
                }
            },
            function(error){
                alert(error);
            }
        )

    });

    $("#worker-search-btn").click(function () {
        var workername=$("#worker-name-input").val();
        postRequest(
            '/search?name='+workername,
            null,
            function (res){
                if(res.message=="该账号不存在"){
                    alert(res.message);
                }
                else{
                    alert('该工作人员密码为：'+res.content.password);
                }
            },
            function(error){
                alert(error);
            }
        )

    });

    function getWorkerList() {
        postRequest(
            '/all',
            null,
            function (res) {
                $('.worker-on-list').empty();
                var workerStr =" ";
                var index=1;
                res.content.forEach(function (v) {
                    workerStr +=
                        "<tr>"+
                        "<td>" + index + "</td>"+
                        "<td>" + v.name + "</td>"+
                        "<td>" + v.password+ "</td>"+
                        "</tr>";
                    index=index+1;
                });
                $('.worker-on-list').append(workerStr);
            },
            function(error){
                alert(error);
            }
        )
    }


});
