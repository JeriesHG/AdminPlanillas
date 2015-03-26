var mainJS = {
	isFloatKey: function (evt) {
		var val = evt.target.value;
		if (isNaN(val)) {
			val = val.replace(/[^0-9\.]/g, '');
			if (val.split('.').length > 2)
				val = val.replace(/\.+$/, "");
		}
		evt.target.value = val;
	},
}