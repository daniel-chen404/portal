$(document).ready(function(){
    //导航
    var timeout         = 10;
    var closetimer		= 0;
    var ddmenuitem      = 0;

    function jsddm_open()
    {	jsddm_canceltimer();
        jsddm_close();
        ddmenuitem = $(this).find('ul').eq(0).css('visibility', 'visible');
        $(this).find('a').eq(0).css("color","#fff600");
        $(this).find('ul').eq(0).find('li:last-child').find('a').css('background',"url('../img/nav_2.png') no-repeat 0 -3px");
    }

    function jsddm_close()
    {	if(ddmenuitem) ddmenuitem.css('visibility', 'hidden');
    }

    function jsddm_timer()
    {	closetimer = window.setTimeout(jsddm_close, timeout);
        $(this).find('a').eq(0).css("color","#fff");
        }

    function jsddm_canceltimer()
    {	if(closetimer)
        {	window.clearTimeout(closetimer);
            closetimer = null;}}

  	$('#jsddm > li').bind('mouseover', jsddm_open);
    $('#jsddm > li').bind('mouseout',  jsddm_timer);

    document.onclick = jsddm_close;
    //banner轮播
    //文字滚动
    $("#scrollDiv").Scroll({line:1,speed:500,timer:1500});

    $(".tenderMenu ul li").mouseover(function(){
//        $(".tenderMenu ul li").find('a').removeClass('aLink');
//        $(".tenderMenu ul li").find('.level2').hide();
       $(this).find('.level2').toggle();
       $(this).find('a').addClass('aLink');
       $(this).find('.level2').find('li:last-child').find('a').css('border',"none");
       $(".tenderMenu ul li").mouseout(function(){
           $(".tenderMenu ul li").find('.level2').hide();
           $(".tenderMenu ul li").find('a').removeClass('aLink');
       });
    })
    
});

//滚动插件
(function($){
$.fn.extend({
        Scroll:function(opt,callback){
                //参数初始化
                if(!opt) var opt={};
                var _this=this.eq(0).find("ul:first");
                var        lineH=_this.find("li:first").height(), //获取行高
                        line=opt.line?parseInt(opt.line,10):parseInt(this.height()/lineH,10), //每次滚动的行数，默认为一屏，即父容器高度
                        speed=opt.speed?parseInt(opt.speed,10):500, //卷动速度，数值越大，速度越慢（毫秒）
                        timer=opt.timer?parseInt(opt.timer,10):3000; //滚动的时间间隔（毫秒）
                if(line==0) line=1;
                var upHeight=0-line*lineH;
                //滚动函数
                scrollUp=function(){
                        _this.animate({
                                marginTop:upHeight
                        },speed,function(){
                                for(i=1;i<=line;i++){
                                        _this.find("li:first").appendTo(_this);
                                }
                                _this.css({marginTop:0});
                        });
                }
                //鼠标事件绑定
                _this.hover(function(){
                        clearInterval(timerID);
                },function(){
                        timerID=setInterval("scrollUp()",timer);
                }).mouseout();
        }        
})
})(jQuery);

