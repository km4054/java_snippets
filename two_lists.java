
// sourceUsersList := an ordered list of users of current subject		
		
// admin can see all users -> we have to prepend users, which are not contained in the sourceUsersList to the top of the 
// list and order them accordingly
if (currentSession.isUserAdmin()) {
	List<SmUsers> allUsers = new ArrayList<>();
	if (msgType.equals("EMAIL")) allUsers = smDB.findUsersForNotifications();
	else if (msgType.equals("PUSH")) allUsers = smDB.findUsersForNotificationsFCM();
	if (allUsers != null) {
		// Efficiently calculate the difference between the two arraylist, so that the users are not duplicated
		HashSet<SmUsers> current = new LinkedHashSet<>(sourceUsersList);
		HashSet<SmUsers> all = new LinkedHashSet<>(allUsers);
		all.removeAll(current);
		List<SmUsers> allList = new ArrayList<>(all);
		Collections.sort(allList, new Comparator<SmUsers>() {
			@Override 
			public int compare(SmUsers o1, SmUsers o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		sourceUsersList.addAll(allList);
	}			
}		