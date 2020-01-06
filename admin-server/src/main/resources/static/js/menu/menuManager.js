var app = new Vue({
    el: "#app",
    data: {
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        data: []
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            var self = this;
            Vue.http.get('../../menu/all',
                {

                }
            ).then(function (res) {
                var data = res.body
                if (data.status === 0) {
                    self.data = data.data
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常')
            });
        },
        handleNodeClick(data) {
            console.log(data)
        }
    }
});