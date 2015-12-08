

(function(exports) {

    var eg = {
        debug: true
    };
    var origin = '*';
    var listeners = {};



    var share = {
        title: function(t) {
            parent.postMessage({
                type: 'title',
                data: t
            }, origin);
            if (eg.debug) {
                console.log('title: %s', t);
            }
            return this;
        },
        desc: function(content) {
            parent.postMessage({
                type: 'desc',
                data: content
            }, origin);
            if (eg.debug) {
                console.log('desc: %s', content);
            }
            return this;
        },
        icon: function(url) {
            parent.postMessage({
                type: 'icon',
                data: url
            }, origin);
            if (eg.debug) {
                console.log('icon: %s', url);
            }
            return this;
        },
        param: function(obj) {
            var r = '?';
            Object.keys(obj).forEach(function(d, i) {
                if (i > 0){
                    r += '&';
                }
                r += d + '=' + obj[d];
            });
            parent.postMessage({
                type: 'param',
                data: r
            }, origin);
            if (eg.debug) {
                console.log('param: %s', r);
            }
            return this;
        },
        trigger: function() {
            parent.postMessage({
                type: 'trigger'
            }, origin);
            if (eg.debug) {
                console.log('triggered ');
            }
            return this;
        },
        popup: function(){
            this.trigger();
        },
        onshared: function(cb){
            var sk = listeners.shared = listeners.shared || [];
            sk.push(cb);
            parent.postMessage({
                type: 'onshared'
            }, origin);
            return this;
        }
    };

    eg.share = function(data) {
        if (data) {
            share.title(data);
        }
        return share;
    };

    eg.uishare = function(s, cb) {
        parent.postMessage({
            type: 'showui',
            message: s,
            playagain: cb
        }, origin);
        if (eg.debug) {
            console.log('showui ',t);
        }
        return share;
    };

    eg.score = function(s) {
        parent.postMessage({
            type: 'score',
            data: s
        }, origin);
        if (eg.debug) {
            console.log('score ',s);
        }
    };

    eg.status = function(s) {
        parent.postMessage({
            type: 'status',
            data: s
        }, origin);
        if (eg.debug) {
            console.log('status %s', s);
        }
    };

    eg.more = function() {
        parent.postMessage({
            type: 'more',
            data: null
        }, origin);
        if (eg.debug) {
            console.log('更多游戏');
        }
    };
    eg.about = function() {
        parent.postMessage({
            type: 'about',
            data: null
        }, origin);
        if (eg.debug) {
            console.log('关于我们');
        }
    };
    eg.showad = function(data) {
        parent.postMessage({
            type: 'showad',
            data: data
        }, origin);
        if (eg.debug) {
            console.log('showad %s', JSON.stringify(data));
        }
    }

    window.addEventListener('message', function(event) {
        var type = event.data.type;
        switch (type) {
            case 'playagain':
                event.data.playagain.call(null);
                break;
            case 'shared':
                if(listeners.shared && listeners.shared.length){
                    listeners.shared.forEach(function(d){
                        d.call(null);
                    });
                }
                console.log('shared');
                break;
            default:
        }
    });
    exports.eg = eg;

})(window);
