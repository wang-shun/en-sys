

directives.directive('c2ChangepermJob',['Modal','$window', function (Modal,$window) {
    return {
    	restirct: 'EA',
    	replace: true,
    	template:'<li><a ng-click="changeJob()"><i class="ace-icon fa fa-exchange"></i>切换岗位</a></li>',
        link:function(scope,element,attr){
        	scope.changeJob = function(){
        		Modal.open('f/changeJob',{},function(result){
    				if('success'==result){
    					$window.location.reload();
    			    }
    			});
        	}
        }
    };
}]);