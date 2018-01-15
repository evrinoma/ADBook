(function() { alert(\"call\");console.log(document); var mode = document.designMode; document.designMode = \"on\"; document.execCommand(\"copy\"); document.designMode = mode;alert(\"end call\");})()
