
function changegroupcontent(element){
    var newHTML = "OMG IT WORKED!";
    document.getElementById(element).innerHTML = newHTML;
    alert("CLICKED!");
}
//$('#GroupContent').click(function(){
 //   this.html('it worked');
//});
function changegroupcontent(element, groupID){
                var newHTML = '<div class = "well">' + groupID + '</div>';
                document.getElementById(element).innerHTML = newHTML;
            }

//$('#GroupContent').html(#{agroup.getID()});return false;