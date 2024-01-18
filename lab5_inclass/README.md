Before I start, I just want to say that the given code was the incarnation of my nightmares and reviewing it was truly painful and 
bone-chilling. This kind of code is the reason why I'm spending nights wandering why I chose to study CS.<br><br><br>

<h1>The deadlock</h1><br>
<h2>The locks that generate the deadlock:</h2>
The ```lock``` object in ```AbstractProcessor``` and the ```groupLocks``` locks in ```PendingRequests``` 
are not being blocked in order. As a matter of fact, the ```rwLock``` in ```PendingRequests``` could have also raised issues 
if it was locking for writing. (right now, it doesn't have an effect on the behaviour since only the readLock is used)<br>
<h2>How to solve the deadlock:</h2>
The fact that the code is so allambicated 


<br><br>
<h1>Major issues</h1><br>

<h2>1) AbstractProcessor synchronisation</h2><br>
In ```AbstractProcessor```, due to lines 39-42 not being synchronised, it is possible, among others, 
for two threads to insert a message in the list, leading to a lost update.<br>

<h2>2) PendingRequest synchronisation</h2><br>
Write locks are never used, despite them being needed in the remove method.<br>
Return statement in a try block without unlocking resources.<br>
```getPendingRequestIds``` is not synchronised.<br>


<br><br>
<h1>Implementation Issues</h1><br>

<h2>0) Package structure</h2><br>
Inconsistent package naming: ```model``` at singular, ```messages``` at plural, ```util``` at singular and, 
to top it all off, the util classes are named ```Utils```<br>
Personal opinion: ```util``` package should not be in the ```model``` package
<br>

<h2>1) Messages class structure</h2><br>
Message class should be an interface.<br>
MessageDataModel should have other fields, should have neither redundant fields, nor getters for them.<br>
MessageDataModel constructor does not have validations.<br>
RecoveryMessage should only extend its base class -> redundant fields.<br>
RecoveryMessage can use composition instead of inheritance, 
    but I choose to leave it like this since composition would create redundant code.<br>
<br>

<h2>2) Utils classes</h2><br>
Useless private constructors since their methods are all static.<br>
Not used method in ```Utils``` class.<br>
Personal opinion: ```null == rwLock```  should not be used, instead I prefer ```rwLock == null``` 
(```rwLock = null``` gives a syntax error anyway)
<br>

<h2>3) AbstractProcessor</h2>  <br>
Pointless creation of ```lock``` object. Instead of ```synchronized (lock)```, ```synchronized (storedList)``` should be used.<br>
Lines 39-42 are repeated and redundant. Also, they may lead to synchronisation issues.<br>

<h2>4) PendingRequests</h2> <br>
In method ```getPendingRequestIds``` the if condition is never true.<br>
In method ```removeRequest``` local variable reqIdsToRemove is not needed.<br>
First try block is pointless.<br>
Methods ```savePendingRequest``` and ```recover``` are never executed despite being called.<br>

I have grown tired of writing minor issues, so I'm just going to refactor the code instead.
