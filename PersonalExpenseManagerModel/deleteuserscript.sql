Delete from pemdb.account where uid=3;
Delete from pemdb.usersettings where userId=3;
Delete from pemdb.pemuser where uid=3;

select txnGroupId from pemdb.transactiongroup where uid=3;
select * from pemdb.transactiontable where txnGroupId=3;
Delete from pemdb.transactiontable where txnGroupId=3;
Delete from pemdb.transactiongroup where uid=3;