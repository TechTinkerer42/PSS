var interval =1200000;
var lefttime=interval;
var hasPopedUp = false;
interval = setInterval('change()',3000);

function change()
{
   lefttime = lefttime - 3000;
   if(lefttime<=60000 && hasPopedUp == false) {
	   sessionOutWindow = window.open('','','width=200,height=200,menubar=false,resizable=false,status=false,titlebar=false,toolbar=false,location=false');
	   sessionOutWindow.document.write("<p>Your Session will expire in next 1 mins</p>");
	   sessionOutWindow.document.title="ETS ERegistration";
	   sessionOutWindow.focus();
	   hasPopedUp = true;
   }
}

function resetExpireTime()
{
	lefttime = interval;
}
