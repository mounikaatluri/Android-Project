/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Volumes/Data/work/EvilApp/app/src/main/aidl/com/cs155/trustedapp/IGetContactsString.aidl
 */
package com.cs155.trustedapp;
public interface IGetContactsString extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.cs155.trustedapp.IGetContactsString
{
private static final java.lang.String DESCRIPTOR = "com.cs155.trustedapp.IGetContactsString";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.cs155.trustedapp.IGetContactsString interface,
 * generating a proxy if needed.
 */
public static com.cs155.trustedapp.IGetContactsString asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.cs155.trustedapp.IGetContactsString))) {
return ((com.cs155.trustedapp.IGetContactsString)iin);
}
return new com.cs155.trustedapp.IGetContactsString.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_GetContacts:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.GetContacts(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.cs155.trustedapp.IGetContactsString
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String GetContacts(java.lang.String paramString) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(paramString);
mRemote.transact(Stub.TRANSACTION_GetContacts, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_GetContacts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.lang.String GetContacts(java.lang.String paramString) throws android.os.RemoteException;
}
