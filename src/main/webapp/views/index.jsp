<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<!DOCTYPE html>
<html>
<head>
<tf:jsinclude></tf:jsinclude>
<meta charset="UTF-8">
<title>Shortener - Shorten URLs</title>
<style>
	.short-link {
		font-size: 18px;
		background-color: #d8dadc;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-5 offset-md-4">
				<br />
				<c:if test="${not empty error }">
					<div class="alert alert-danger">
						${error }
					</div>
				</c:if>
				<div class="h2">URL Shortener</div>
				<c:if test="${shortend != null}">
					<div>
						<span id="shortLink" class="short-link">${baseURL}${shortend.key}</span>
						<span><a href="#" id="copyLink" onClick="copyToClipboard()" class="btn btn-sm btn-primary">Copy</a></span>
						<span id="copySuccess" style="visibility:hidden">Copied!</span>
					</div>
				</c:if>
				<form action="shorten">
					<div class="form-group">
						<label for="longURL">Your URL Here:</label>
						<input class="form-control" type="text" name="longURL" />
					</div>
					<button class="btn btn-primary" type="submit">Shorten!</button>
				</form>
			</div>
		</div>
	</div>
	
	<script>
	function fallbackCopyTextToClipboard(text) {
		  var textArea = document.createElement("textarea");
		  textArea.value = text;
		  document.body.appendChild(textArea);
		  textArea.focus();
		  textArea.select();

		  try {
		    var successful = document.execCommand('copy');
		    var msg = successful ? 'successful' : 'unsuccessful';
		    console.log('Fallback: Copying text command was ' + msg);
		  } catch (err) {
		    console.error('Fallback: Oops, unable to copy', err);
		  }

		  document.body.removeChild(textArea);
		}
		function copyTextToClipboard(text) {
		  if (!navigator.clipboard) {
		    fallbackCopyTextToClipboard(text);
		    return;
		  }
		  navigator.clipboard.writeText(text).then(function() {
		    console.log('Async: Copying to clipboard was successful!');
		  }, function(err) {
		    console.error('Async: Could not copy text: ', err);
		  });
		}
		
		function copyToClipboard() {
			copyTextToClipboard(document.getElementById("shortLink").textContent);
			document.getElementById("copySuccess").style.visibility = 'visible';
			setTimeout(() => document.getElementById("copySuccess").style.visibility = 'hidden', 2000);
			return false;
		}
	</script>
</body>
</html>