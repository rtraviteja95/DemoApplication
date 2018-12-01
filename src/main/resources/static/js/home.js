/**
 * 
 */

function loadFilesIntoTable(){
	
	var request = new XMLHttpRequest();
	request.open("GET", "/userfm/userFiles");

	request.onload = function() {
		var response = JSON.parse(request.responseText);
		if(request.status == 200) {
			document.getElementById("errorList").style.display = "none";
			var tableData = document.getElementById("fileDataBody");
			tableData.innerHTML = '';
			if(response.length > 0){
				for(var i=0; i < response.length; i++){
					var data = response[i];
					
					var trElement = document.createElement('tr');
					
					var tdElement1 = document.createElement('td');
					tdElement1.innerHTML = data.fileName;
					trElement.appendChild(tdElement1);
					
					var tdElement2 = document.createElement('td');
					var date = new Date(data.storedDate);
					tdElement2.innerHTML = date.toString("MMM-dd-yyyy");;
					trElement.appendChild(tdElement2);
					
					var tdElement3 = document.createElement('td');
					
					var button = document.createElement('button');
					button.innerHTML = '<a href="/userfm/downloadFile/'+data.fileName+'" style="color: #FFFFFF;">Download</a>';
					button.className = 'btn btn-warning';
					
					tdElement3.appendChild(button);
					trElement.appendChild(tdElement3);
					
					tableData.appendChild(trElement);
				}
			}else{
				document.getElementById("errorList").style.display = "none";
				document.getElementById("errorList").innerHTML = "No Files uploaded!";
				document.getElementById("errorList").style.display = "block";
			}
		} else {
			document.getElementById("errorList").style.display = "none";
			document.getElementById("errorList").innerHTML = (response && response.message) || "Please try again!";
			document.getElementById("errorList").style.display = "block";
		}
	}
	request.send( null );

}

function downloadFile(file){
	var request = new XMLHttpRequest();
	request.open("GET", "/userfm/downloadFile/"+file);
	request.send( null );
}

function uploadFile() {

	var fileVar = file.files;
	if(fileVar.length === 0) {
		document.getElementById("error").innerHTML = "Please select file to upload";
		document.getElementById("error").style.display = "block";
		return;
	}

	var formData = new FormData();
	formData.append("file", fileVar[0]);

	var request = new XMLHttpRequest();
	request.open("POST", "/userfm/uploadFile");

	request.onload = function() {
		var response = JSON.parse(request.responseText);
		if(request.status == 200) {
			document.getElementById("error").style.display = "none";
			document.getElementById("error").innerHTML = "Uploaded Successfully";
			document.getElementById("error").style.display = "block";
			
			loadFilesIntoTable();
		} else {
			document.getElementById("error").style.display = "none";
			document.getElementById("error").innerHTML = (response && response.message) || "Please try again!";
			document.getElementById("error").style.display = "block";
		}
	}

	request.send(formData);
}