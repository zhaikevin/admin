function authentication(codes, authData, self) {
    var menuCodes = []
    Object.keys(codes).forEach(function (key) {
        menuCodes.push(
            {
                menuCode:key
            }
        )
    })
    Vue.http.get('../../menu/buttonAuthentication',
        {
            params:
                {
                    buttons: JSON.stringify(menuCodes)
                }
        }).then(function (res) {
        var data = res.body;
        if (data.status === 0) {
            for (var i = 0; i < data.data.length; i++) {
                var authorityKey = data.data[i].menuCode
                authData[codes[authorityKey]] = data.data[i].flag
            }
        } else {
            self.$message.error(data.statusInfo)
        }
    }, function () {
        self.$message.error('获取权限信息异常')
    });
}