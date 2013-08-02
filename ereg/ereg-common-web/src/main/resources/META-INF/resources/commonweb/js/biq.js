/* ===================================================
 *  Written by Irfan Ali
 
 * ========================================================== */
      
		  
		      
		    	  
			/*	$();
		    	 
		       
		       function prepareHash(object, triggerArray, index){
		    	   var hash = [];
		    	   $.each(triggerArray, function(index, value) {
		    		   hash[value[0]] = value[1];
		    		   alert(value[0] + " : " + hash[value[0]]);
		    	   });
		  
		    	  $("#container").html($("#div" + hash[index]));
			  */
			      
		   /*    };
		       
		       function toggleDisplay(index) {
		    	   
		       }*/
		       
		/*        function hideQuestion() {
		    	   $("[id^=q]").hide();
		    	   $("#q1").show();
		       } */
		       

		       
		      //$("[id^=q]").hide();
		       //$("#q3").show();
		       
		       
		     //  $("#q4").hide();
		       
		        function toggleDisplay(object, triggerArray){
		    	   
		    	   var enabledQuestions = new Array();
		    	  
		    	   var selected = $(object).val();
		      	  
		    	   //enabled questions list
		    	   $.each(triggerArray, function(index, value) {
		   		   if(selected == value[0]){
					   enabledQuestions[index] = value[1];
		   		   }
		      	  });
		      	  
		      	  $.each(triggerArray, function(index, value) {
		    		 //alert("index:" + index); 
		    		 // alert("value:" + value);
		      		  //hash[value[0]] = value[1];
		    		   //alert(value[0] + " : " + hash[value[0]]);
		    		 //  alert(selected + " == " + value[0]);

		    		   if(selected == value[0]){
		    			   $("div#q"+value[1]).animate({ height: 'show', opacity: 'show' }, 'slow');
		    		   }else{
		    			   //alert("enabledQuestions" + enabledQuestions);
		    			   //alert(searchString(enabledQuestions, value[1]));
		    			   if(!searchString(enabledQuestions, value[1])){
		    				   $("div#q"+value[1]).animate({ height: 'hide', opacity: 'hide' }, 'slow');
		    			   }
		    		   }
		    	   });
		    	   //hideQuestion();
		    	  //$("[id^=q]").hide();
		         //  $("#q3").show();
		          
		    	   
		    	   //$("div#q"+hash[index]).show();
		    	   
		    	   //alert (triggerArray);
		    	  // alert (object.id);
		    	  
		       };
		       
		       function searchString (arrayToSearch, stringToSearch) {
		    	    arrayToSearch.sort();

		    	    for (var i = 0; i < arrayToSearch.length; i++) {
		    	        // need to use a double equals sign "==" to test for equality
		    	        if (arrayToSearch[i] == stringToSearch)
		    	            return true;
		    	    // following code is unnecessary
		    	      //  else if (stringToSearch < arrayToSearch[i])
		    	          //  return false;
		    	     }
		    	    return false;
		    	}
		       
		       
	
		       

