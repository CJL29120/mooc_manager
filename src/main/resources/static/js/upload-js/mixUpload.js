var accessid = '',
    host = '',
    policyBase64 = '',
    signature = '',
    callbackbody = '',
    filename = '',
    key = '',
    expire = 0,
    g_object_name = '',
    now = timestamp = Date.parse(new Date()) / 1000,
    mixUrl = '';

function send_request()
{
    var xmlhttp = null;
    if (window.XMLHttpRequest)
    {
        xmlhttp=new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    if (xmlhttp!=null)
    {
        // serverUrl是 用户获取 '签名和Policy' 等信息的应用服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        serverUrl = 'http://localhost:2020/upload/getPolicyAndCallback'

        xmlhttp.open( "GET", serverUrl, false );
        xmlhttp.send( null );
        return xmlhttp.responseText
    }
    else
    {
        alert("Your browser does not support XMLHTTP.");
    }
};

function get_signature()
{
    // 可以判断当前expire是否超过了当前时间， 如果超过了当前时间， 就重新取一下，3s 作为缓冲。
    now = timestamp = Date.parse(new Date()) / 1000;
    if (expire < now + 3)
    {
        body = send_request()
        var obj = eval ("(" + body + ")");
        host = obj['host']
        policyBase64 = obj['policy']
        accessid = obj['accessid']
        signature = obj['signature']
        expire = parseInt(obj['expire'])
        callbackbody = obj['callback']
        key = obj['dir']
        console.log(body);
        return true;
    }
    return false;
};

function random_string(len) {
    len = len || 32;
    var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function calculate_object_name(filename) {
    suffix = get_suffix(filename)
    g_object_name = key + random_string(10) + suffix
    return ''
}

function get_uploaded_object_name() {
    return g_object_name
}

function set_upload_param(up, filename, ret) {
    if (ret == false)
    {
        ret = get_signature()
    }
    g_object_name = key;
    if (filename != '') {
        calculate_object_name(filename)
    }
    new_multipart_params = {
        'key' : g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid,
        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
        'callback' : callbackbody,
        'signature': signature,
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

function setClipText(text){
    $(document.body).bind("copy",function(e) {
        var clipboardData = window.clipboardData;
        if (!clipboardData) { // for chrome
            clipboardData = e.originalEvent.clipboardData;
        }
        clipboardData.setData('Text', text);
        return false;
    });
}
function copyFunc() {
    document.designMode = "on";
    document.execCommand('copy', true);
    document.designMode = "off";
    $("#html").html("");
}
function showLink() {
    var html = "";
    $("#html").html("");
    iarr = ["jpg","gif","png","bmp"];
    varr = ["mpg","m4v","mp4","flv","3gp","mov","avi","rmvb","mkv","wmv"];
    var fileType = mixUrl.substring(mixUrl.lastIndexOf(".")+1,mixUrl.length);

    $.each(iarr,function (key,value) {
        if(fileType === value){
            console.log(fileType);
            setClipText(mixUrl)
            html = '<span id="link" style=" font-size:8px;">'+mixUrl+'<a href="javascript:void(0)" onclick="copyFunc()">（点击复制）</a></span>'
            $("#html").append(html);
        }
    });
    $.each(varr,function (key,value) {
        if(fileType === value){
            console.log(fileType);
            setClipText("<iframe src='"+ mixUrl +"'></iframe>");
            html = '<span id="link" style=" font-size:8px;">&lt;iframe src="'+mixUrl+'"&gt;&lt;/iframe&gt;<a href="javascript:void(0)" onclick="copyFunc()">（点击复制）</a></span>';
            $("#html").append(html);
        }
    });
}
var uploader = new plupload.Uploader({
    runtimes: 'html5,flash,silverlight,html4',
    browse_button: 'selectfiles',
    multi_selection: false,
    container: document.getElementById('container'),
    flash_swf_url: '/js/upload-js/lib/plupload-2.1.2/js/Moxie.swf',
    silverlight_xap_url: '/js/upload-js/lib/plupload-2.1.2/js/Moxie.xap',
    url: 'http://oss.aliyuncs.com',

    filters: {
        mime_types: [ //只允许上传图片、视频文件
            {title: "Image files", extensions: "jpg,gif,png,bmp"},
            {title: "video files", extensions: "mpg,m4v,mp4,flv,3gp,mov,avi,rmvb,mkv,wmv"}
        ],
        max_file_size: '50mb', //最大只能上传10mb的文件
        prevent_duplicates: true //不允许选取重复文件
    },

    init: {
        PostInit: function () {
            document.getElementById('ossfile').innerHTML = '';
            document.getElementById('postfiles').onclick = function () {
                set_upload_param(uploader, '', false);
                return false;
            };
        },

        FilesAdded: function (up, files) {
            if(up.files.length>1) {
                alert("只能上传一个文件，请删除多余文件！");
                $('#upload-pc').attr("src","");
                $('#'+up.files[0].id).hide();
                uploader.splice();//清空上传队列
                return;
            }

            plupload.each(files, function (file) {
                document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                    + '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                    + '</div>';
            });
        },

        BeforeUpload: function (up, file) {
            set_upload_param(up, file.name, true);
        },

        UploadProgress: function (up, file) {
            var d = document.getElementById(file.id);
            d.getElementsByTagName('b')[0].innerHTML = '<h4>上传进度：</h4>'+'<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
            var progBar = prog.getElementsByTagName('div')[0]
            progBar.style.width = 2 * file.percent + 'px';
            progBar.setAttribute('aria-valuenow', file.percent);
        },

        FileUploaded: function (up, file, info) {
            if (info.status == 200) {
                mixUrl = host+"/"+get_uploaded_object_name();
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传成功!';
                showLink();
            } else if (info.status == 203) {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传到OSS成功，但是oss访问用户设置的上传回调服务器失败，失败原因是:' + info.response;
            } else {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            }
        },

        Error: function (up, err) {
            if (err.code == -600) {
                document.getElementById('console').appendChild(document.createTextNode("\n选择的文件太大了"));
            } else if (err.code == -601) {
                document.getElementById('console').appendChild(document.createTextNode("\n选择的文件后缀不对"));
            } else if (err.code == -602){
                //    选择同一个文件
            }  else {
                document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
            }
        }
    }
});
uploader.init();