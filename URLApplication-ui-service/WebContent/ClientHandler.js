var tinyGenUrl = "http://localhost:8080/URLApplication-server/";

function generateTinyUrl() {
	// AJAX Call to POST ID
	var textField = document.getElementById("url");
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var response = this.responseText;
				console.log(response);
				if (response) {
					
					document.getElementById("tiny").innerHTML = tinyGenUrl + response;
					document.getElementById("tiny").setAttribute("href", tinyGenUrl + response);
					textField.value = "";
				}
			}
		}
	};
	xmlhttp.open("POST", tinyGenUrl, true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	var url = textField.value;
	if(isValidURL(url)){
		xmlhttp.send("url=" + url);
	}
};

function redirect() {
	var tinyUrl = document.getElementById("tiny").innerHTML;
	var id = tinyUrl.split('/')[4];
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var response = this.responseText;
				console.log(response);
				
			}
		}
	};
	xmlhttp.open("GET", tinyGenUrl + '?id=' + id, true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlhttp.send(null);
};
function isValidURL(str) {
	var regex = /(http|https):\/\/(\w+:{0,1}\w*)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/;
	  if(!regex .test(str)) {
	    alert("Please enter valid URL.");
	    return false;
	  } else {
	    return true;
	  }
	}
