/**
 * 
 */
$('document').ready(function(){
	
	$(".loading").click(function(){
		$("#loading").show();
	})
	
	/*$('.table .btn').on('click', function(event){
		
		event.preventDefault();
		
		var href = $(this).attr('href');
	
		$.get(href, function(items, status){
		
			$("#id").val(items.id);
			$("#editDescription").val(items.description);
			$("#editQuantity").val(items.quantity);
			$("#editUnit").val(items.unit);
			$("#editHsCode").val(items.hsCode);	
			$("#editChangeHsCode").val(items.hsCode);	
			$("#editValue").val(items.value);
			$("#editDuty").val(items.importDuty);
			$("#editExcise").val(items.excise);

		});

		$('#editModal').modal();
	
	});*/
	
	$('.table #deleteButton').on('click', function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteModal #deleteId').attr('href',href);
		$('#deleteModal').modal();
		
	});
	
	$('#appe').on('click', function (){
		/*<![CDATA[*/
	    var path = /*[[@{/}]]*/'updateApplSup';
	    /*]]>*/
		
		var id=$(this).attr('id');
		event.preventDefault();
		
		bootbox.confirm({
			message: "Are you sure to Approve this DutyFree Application?",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.post(path, {'id':id}, function(res) {
						location.reload();
					});
				}
			}
		});
	});
	
	$('#approve').click(function(e) {
	    e.preventDefault();
	    bootbox.confirm("Are you sure to Approve this DutyFree Application?", function(result) {
	        if (result) {
	            $('#commentSup').submit();
	        }
	    });
	});

	$('#commentSup').on('submit', function() {
	    alert('Application has been elevated to the commisioner of customs for Approval.');
	})

	
	$('#return').on('click', function (){
		/*<![CDATA[*/
	    var path = /*[[@{/}]]*/'return?applId='+ $('#applId').val();
	    /*]]>*/
		
	    event.preventDefault();
		var id=$(this).attr('id');
		
		
		bootbox.confirm({
			message: "Are you sure you want to return this DutyFree Application?",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.post(path, {'id':id}, function(res) {
						top.location.href = '/appList';
					});
				}
			}
		});
	});
	
	$('#reject').on('click', function (){
		/*<![CDATA[*/
	    var path = /*[[@{/}]]*/'reject?applId='+ $('#applId').val();
	    /*]]>*/
		
	    event.preventDefault();
		var id=$(this).attr('id');
		
		bootbox.confirm({
			message: "Are you sure to Reject this DutyFree Application?",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.post(path, {'id':id}, function(res) {
						top.location.href = '/appList';
					});
				}
			}
		});
	});
	
});