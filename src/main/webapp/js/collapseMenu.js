(function($){
	$.fn.collapseMenu = function(options) {

		// **************global variables define****************
		var defaults = {
			indent : 14,
			speed : 400,
			data : [],
			queryParams: {},
			dataUrl : "",
			target : "menuTarget"
		};
		var opts = $.extend(defaults, options);
		var DEFAULT_ICON_CLS = "glyphicon glyphicon-home";
		var DEFAULT_HREF = "javascript:void(0);";
		var $t = $(this);
		var $target = $('#' + opts.target);

		// *****************************************************

		buildMenu();
		

		function buildMenu() {
			// to build the menu
			var dataUrl = opts.dataUrl;
			var queryParams = opts.queryParams;
			if(typeof(dataUrl) == "string" && dataUrl.length > 0) {
				// debug('use dataUrl arg');
				$.getJSON(dataUrl, queryParams, function(data){
					// debug(data);
					opts.data = data;
					var $menuGroup = _buildMenu(opts.data);
					// debug($menuGroup.html());
					$t.append($menuGroup); 
					initMenu();
				});
			} else {
				var $menuGroup = _buildMenu(opts.data);
				$t.append($menuGroup);
				initMenu();
			}

		};

		function _buildMenu(data) {
			var $menuGroup = $('<ul class="menu-group"></ul>');
			var data = data;
			if(typeof(data) == "undefined" || !(data instanceof Array)) {
				debug("error of the data type, can not build the menu");
				return ;
			}
			for(var i = 0; i < data.length; i++) {
				var item = data[i];
				// debug(item);
				var children = item.children;
				if(typeof(children) == "undefined" || !(data instanceof Array)) {
					// process menu item with no children
					var itemContent = buildMenuItem(item);
					$menuGroup.append(itemContent);
				} else {
					// process menu item with children
					var subMenuHeadingContent = buildSubMenuHeading(item);
					$menuGroup.append(subMenuHeadingContent);
					var $subMenuGroup = _buildMenu(children);
					$menuGroup.append($subMenuGroup);
				}
			}
			return $menuGroup;
		}

		function buildSubMenuHeading(item) {
			var item = item;
			var text = item.text;
			var iconCls = item.iconCls;
			if(typeof(iconCls) == "undefined" || typeof(iconCls) != "string" || iconCls.length == 0) {
				iconCls = DEFAULT_ICON_CLS;
			}
			// <a class="submenu-heading">Home Page<span class="menu-collapse-closed"></span></a>
			var html = '<a class="submenu-heading"></span><span class="' + iconCls + '">&nbsp;</span>' + 
						text + '<span class="menu-collapse-closed"></a>';
			return html;
		}

		function buildMenuItem(item) {
			var item = item;
			var text = item.text;
			var iconCls = item.iconCls;
			var href = item.href;
			if(typeof(iconCls) == "undefined") {
				iconCls = DEFAULT_ICON_CLS;
			}
			if(typeof(href) == "undefined") {
				href = DEFAULT_HREF;
			}
			// <a class="menu-item active"><span class="glyphicon glyphicon-home">&nbsp;</span>Home Page</a>
			var html = '<a class="menu-item" href="' + href + '"><span class="' + iconCls + '">&nbsp;</span>' + text + '</a>';
			return html;
		};
		
		function initMenu() {
			$t.children().each(function(){
				var $menuGroup = $(this);
				$menuGroup.find('.submenu-heading').each(function(){
					// iterate the sub menu heading
					var $subMenuHeading = $(this);
					// bind the click event on the sub menu heading
					bindCollapseEvent($subMenuHeading);

					var $subMenu = $subMenuHeading.next();
					var level = countMenuLevel($subMenu, $t);
					// add the padding-left css
					$subMenu.children().not('ul').each(function(){
						var $menuItem = $(this);
						$menuItem.css('padding-left', opts.indent * level + opts.indent);
					});
					// init to be hidden
					$subMenu.hide();
				});
			});

			// bind the click event to menu-item
			$t.find('a.menu-item').each(function(){
				$(this).on('click', function(){
					$t.find('a.active').each(function(){
						$(this).removeClass('active');
					});
					$(this).addClass('active');
					// debug($(this).attr('href'));
					//$target.remove();
					var href = $(this).attr('href');
					$target.load(href);
					return false;
				});
			});

		}; // end function initMenu()

		function bindCollapseEvent(subMenuHeading) {
			var $subMenuHeading = subMenuHeading;
			// bind the click event on the sub menu heading
			$subMenuHeading.on('click', function(){
				var $subMenu = $subMenuHeading.next();
				if($subMenu.is(':hidden')) {
					// expand
					/*$subMenuHeading.children().eq(0)
									.removeClass('menu-collapse-closed')
									.addClass('menu-collapse-open');*/
					var $span = $subMenuHeading.find('span.menu-collapse-closed').eq(0);
					// debug($span.html());
									$span.removeClass('menu-collapse-closed')
									.addClass('menu-collapse-open');
					$subMenu.slideDown(opts.speed);
				} else {
					// fold
					/*$subMenuHeading.children().eq(0)
									.removeClass('menu-collapse-open')
									.addClass('menu-collapse-closed');*/
					var $span = $subMenuHeading.find('span.menu-collapse-open').eq(0);
					// debug($span.html());
									$span.removeClass('menu-collapse-open')
									.addClass('menu-collapse-closed');
					$subMenu.slideUp(opts.speed);
				}
			});
		}; // end bindCollapseEvent(subMenuHeading)

		// count the menu level : 0 <= level <= n
		function countMenuLevel(menu, root) {
			var $root = root;
			var $menu = menu;
			var level = 0;
			while($menu.parent().attr('id') != $root.attr('id')) {
				level++;
				$menu = $menu.parent();
			}
			return level;
		} // end function countMenuLevel(menu, root)

		// debug the msg to the firedebug console
		function debug(msg) {
			console.info(msg);
		};
	};
})(jQuery);