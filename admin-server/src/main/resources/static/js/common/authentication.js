/**
 * 用户组鉴权
 */
function groupAuthentication(groupId) {
    var groupList = JSON.parse(Cookies.get('user_group'))
    if (groupList.length === 0) {
        return false
    }
    for (var i = 0; i < groupList.length; i++) {
        var group = groupList[i]
        if (groupId == group.groupId && group.adminFlag == 2) {
            return true
        }
        if(group.groupCode === 'super') {
            return true
        }
    }
    return false
}

/**
 * 按钮鉴权
 * @param codes
 * @param authData
 * @param self
 */
function authentication(codes, authData, self) {
    var menuCodes = []
    Object.keys(codes).forEach(function (key) {
        menuCodes.push(
            {
                menuCode: key
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