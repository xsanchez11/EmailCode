
//* Triggered by OnChange In Sheets 
function onEdit(event) 
{
  showMessageOnUpdate(event);  
  sendEmailOnUpdate(event);
}

//* Updated

function showMessageOnUpdate(event)
{
 var edited_Column = checkStatusIsUpdated(event);
  if(edited_Column > 0)
  {
    SpreadsheetApp.getUi().alert("Permit expiring!");
  }
}

//* Expiring or Not Expired

function checkStatusIsUpdated(event)
{
  const range = event.range;
  if(range.getColumn() >2)
  {
    var edited_Column = range.getColumn();
    var status = SpreadsheetApp.getActiveSheet().getRange(edited_Column, 5, 26).getValue();
    if(status == '15')
    {
      return edited_Column;
    }
  }
  return 0;
}



//* Send on approval. 

function sendEmailOnUpdate(event)
{
  var updated_Column = checkStatusIsUpdated(event);

  if(updated_Column <= 0)
  {
    return;
  }

  sendEmailByColumn(updated_Column);
}

//* The email.

function sendEmailByColumn(edited_Column)
{
  var values = SpreadsheetApp.getActiveSheet().getRange(edited_Column, 5, 26).getValue();
  var Column_values = values[1];

  var mail = composeUpdatedEmail(Column_values);
  SpreadsheetApp.getUi().alert(" subject is "+mail.subject+"\n message "+mail.message); 
  GMailApp.sendEmail(admin_email,mail.subject,mail.message);
}


//* Composes email. 

function composeUpdatedEmail(Column_values)
{
  var permit_name = Column_values[0];
  
  var days_left = Column_values[1];

  var message = "The following permit should be updated: "+permit_name+"";
  var subject = "Expiring Permit for "+permit_name+" with days left: "+days_left;

  return({message:message,subject:subject});
}






