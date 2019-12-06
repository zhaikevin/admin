function authentication(codes, authDate) {
    var codeStr = ''
    Object.keys(codes).forEach(function (key) {
        codeStr += key + ','
    })

    if (codeStr.length !== 0) {
        codeStr = codeStr.substring(0, codeStr.length - 1)
    }

    // ajax 请求鉴权
    var ajax = new XMLHttpRequest()
    ajax.open('get', '../../authentication/menuAuthentication?codesString=' + codeStr)
    ajax.withCredentials = true
    ajax.send()
    ajax.onreadystatechange = function () {
        if (ajax.readyState === 4 && ajax.status === 200) {
            var authenticationResult = JSON.parse(ajax.responseText)
            var data = authenticationResult.data
            for (var i = 0; i < data.length; i++) {
                var authorityKey = data[i].authorityKey
                authDate[codes[authorityKey]] = data[i].flag
            }
        }
    }
}