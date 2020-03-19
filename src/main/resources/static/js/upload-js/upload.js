   var accessid = '',
    host = '',
    policyBase64 = '',
    signature = '',
    callbackbody = '',
    filename = '',
    key = '',
    expire = 0,
    g_object_name = '',
    now = timestamp = Date.parse(new Date()) / 1000;

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

function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
       if (!file || !/image\//.test(file.type)) return; //确保文件是图片
       if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
           var fr = new mOxie.FileReader();
           fr.onload = function () {
               callback(fr.result);
               fr.destroy();
               fr = null;
           }
           fr.readAsDataURL(file.getSource());
       } else {
           var preloader = new mOxie.Image();
           preloader.onload = function () {
               //preloader.downsize(550, 400);//先压缩一下要预览的图片,宽300，高300
               var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
               callback && callback(imgsrc); //callback传入的参数为预览图片的url
               preloader.destroy();
               preloader = null;
           };
           preloader.load(file.getSource());
       }
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
            {title: "Image files", extensions: "jpg,gif,png,bmp"}
        ],
        max_file_size: '3mb', //最大只能上传10mb的文件
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
                previewImage(file, function(imgSrc) {
                    $("#upload-pc").attr("src", imgSrc);
                });
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
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传成功!';
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

var uploader1 = new plupload.Uploader({
       runtimes: 'html5,flash,silverlight,html4',
       browse_button: 'selectfiles1',
       multi_selection: false,
       container: document.getElementById('container1'),
       flash_swf_url: '/js/upload-js/lib/plupload-2.1.2/js/Moxie.swf',
       silverlight_xap_url: '/js/upload-js/lib/plupload-2.1.2/js/Moxie.xap',
       url: 'http://oss.aliyuncs.com',

       filters: {
           mime_types: [ //只允许上传视频文件
               {title: "video files", extensions: "mpg,m4v,mp4,flv,3gp,mov,avi,rmvb,mkv,wmv"}
           ],
           max_file_size: '30mb', //最大只能上传10mb的文件
           prevent_duplicates: true //不允许选取重复文件
       },

       init: {
           PostInit: function () {
               document.getElementById('ossfile1').innerHTML = '';
               document.getElementById('postfiles1').onclick = function () {
                   set_upload_param(uploader1, '', false);
                   return false;
               };
           },

           FilesAdded: function (up, files) {
               if(up.files.length>1) {
                   alert("只能上传一个文件，请删除多余文件！");
                   $('#'+up.files[0].id).hide();
                   uploader.splice();//清空上传队列
                   return;
               }
               plupload.each(files, function (file) {
                   document.getElementById('ossfile1').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
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
                   document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传成功!';
                   var url = host+"/"+get_uploaded_object_name();
                   $("#video").show().attr("src", url);
                   $("#video").parent().get(0).load();
                   $("#video").parent().show();
               } else if (info.status == 203) {
                   document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传到OSS成功，但是oss访问用户设置的上传回调服务器失败，失败原因是:' + info.response;
               } else {
                   document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
               }
           },

           Error: function (up, err) {
               if (err.code == -600) {
                   document.getElementById('console1').appendChild(document.createTextNode("\n选择的文件太大了,可以根据应用情况，在upload.js 设置一下上传的最大大小"));
               } else if (err.code == -601) {
                   document.getElementById('console1').appendChild(document.createTextNode("\n选择的文件后缀不对,可以根据应用情况，在upload.js进行设置可允许的上传文件类型"));
               } else if (err.code == -602){
               //    选择同一个文件
               } else {
                   document.getElementById('console1').appendChild(document.createTextNode("\nError xml:" + err.response));
               }
           }
       }
   });

uploader.init();
uploader1.init();