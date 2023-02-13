package android.runtime;

import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
import org.xmlpull.v1.XmlPullParser;
/* loaded from: classes.dex */
public class XmlReaderResourceParser extends XmlReaderPullParser implements IGCUserPeer, XmlResourceParser, AttributeSet, XmlPullParser {
    public static final String __md_methods = "n_close:()V:GetCloseHandler:Android.Content.Res.IXmlResourceParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeNamespace:(I)Ljava/lang/String;:GetGetAttributeNamespace_IHandler:Android.Content.Res.IXmlResourceParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeCount:()I:GetGetAttributeCountHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getClassAttribute:()Ljava/lang/String;:GetGetClassAttributeHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getIdAttribute:()Ljava/lang/String;:GetGetIdAttributeHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getPositionDescription:()Ljava/lang/String;:GetGetPositionDescriptionHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getStyleAttribute:()I:GetGetStyleAttributeHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeBooleanValue:(IZ)Z:GetGetAttributeBooleanValue_IZHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeBooleanValue:(Ljava/lang/String;Ljava/lang/String;Z)Z:GetGetAttributeBooleanValue_Ljava_lang_String_Ljava_lang_String_ZHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeFloatValue:(IF)F:GetGetAttributeFloatValue_IFHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeFloatValue:(Ljava/lang/String;Ljava/lang/String;F)F:GetGetAttributeFloatValue_Ljava_lang_String_Ljava_lang_String_FHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeIntValue:(II)I:GetGetAttributeIntValue_IIHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeIntValue:(Ljava/lang/String;Ljava/lang/String;I)I:GetGetAttributeIntValue_Ljava_lang_String_Ljava_lang_String_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeListValue:(I[Ljava/lang/String;I)I:GetGetAttributeListValue_IarrayLjava_lang_String_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeListValue:(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;I)I:GetGetAttributeListValue_Ljava_lang_String_Ljava_lang_String_arrayLjava_lang_String_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeName:(I)Ljava/lang/String;:GetGetAttributeName_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeNameResource:(I)I:GetGetAttributeNameResource_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeResourceValue:(II)I:GetGetAttributeResourceValue_IIHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeResourceValue:(Ljava/lang/String;Ljava/lang/String;I)I:GetGetAttributeResourceValue_Ljava_lang_String_Ljava_lang_String_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeUnsignedIntValue:(II)I:GetGetAttributeUnsignedIntValue_IIHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeUnsignedIntValue:(Ljava/lang/String;Ljava/lang/String;I)I:GetGetAttributeUnsignedIntValue_Ljava_lang_String_Ljava_lang_String_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeValue:(I)Ljava/lang/String;:GetGetAttributeValue_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeValue:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;:GetGetAttributeValue_Ljava_lang_String_Ljava_lang_String_Handler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getIdAttributeResourceValue:(I)I:GetGetIdAttributeResourceValue_IHandler:Android.Util.IAttributeSetInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getColumnNumber:()I:GetGetColumnNumberHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getDepth:()I:GetGetDepthHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getEventType:()I:GetGetEventTypeHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getInputEncoding:()Ljava/lang/String;:GetGetInputEncodingHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_isEmptyElementTag:()Z:GetIsEmptyElementTagHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_isWhitespace:()Z:GetIsWhitespaceHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getLineNumber:()I:GetGetLineNumberHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getName:()Ljava/lang/String;:GetGetNameHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getNamespace:()Ljava/lang/String;:GetGetNamespaceHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getPrefix:()Ljava/lang/String;:GetGetPrefixHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getText:()Ljava/lang/String;:GetGetTextHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_defineEntityReplacementText:(Ljava/lang/String;Ljava/lang/String;)V:GetDefineEntityReplacementText_Ljava_lang_String_Ljava_lang_String_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributePrefix:(I)Ljava/lang/String;:GetGetAttributePrefix_IHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getAttributeType:(I)Ljava/lang/String;:GetGetAttributeType_IHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getFeature:(Ljava/lang/String;)Z:GetGetFeature_Ljava_lang_String_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getNamespace:(Ljava/lang/String;)Ljava/lang/String;:GetGetNamespace_Ljava_lang_String_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getNamespaceCount:(I)I:GetGetNamespaceCount_IHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getNamespacePrefix:(I)Ljava/lang/String;:GetGetNamespacePrefix_IHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getNamespaceUri:(I)Ljava/lang/String;:GetGetNamespaceUri_IHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getProperty:(Ljava/lang/String;)Ljava/lang/Object;:GetGetProperty_Ljava_lang_String_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_getTextCharacters:([I)[C:GetGetTextCharacters_arrayIHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_isAttributeDefault:(I)Z:GetIsAttributeDefault_IHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_next:()I:GetNextHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_nextTag:()I:GetNextTagHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_nextText:()Ljava/lang/String;:GetNextTextHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_nextToken:()I:GetNextTokenHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_require:(ILjava/lang/String;Ljava/lang/String;)V:GetRequire_ILjava_lang_String_Ljava_lang_String_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_setFeature:(Ljava/lang/String;Z)V:GetSetFeature_Ljava_lang_String_ZHandler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_setInput:(Ljava/io/InputStream;Ljava/lang/String;)V:GetSetInput_Ljava_io_InputStream_Ljava_lang_String_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_setInput:(Ljava/io/Reader;)V:GetSetInput_Ljava_io_Reader_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_setProperty:(Ljava/lang/String;Ljava/lang/Object;)V:GetSetProperty_Ljava_lang_String_Ljava_lang_Object_Handler:Org.XmlPull.V1.IXmlPullParserInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n";
    private ArrayList refList;

    private native void n_close();

    private native void n_defineEntityReplacementText(String str, String str2);

    private native boolean n_getAttributeBooleanValue(int i, boolean z);

    private native boolean n_getAttributeBooleanValue(String str, String str2, boolean z);

    private native int n_getAttributeCount();

    private native float n_getAttributeFloatValue(int i, float f);

    private native float n_getAttributeFloatValue(String str, String str2, float f);

    private native int n_getAttributeIntValue(int i, int i2);

    private native int n_getAttributeIntValue(String str, String str2, int i);

    private native int n_getAttributeListValue(int i, String[] strArr, int i2);

    private native int n_getAttributeListValue(String str, String str2, String[] strArr, int i);

    private native String n_getAttributeName(int i);

    private native int n_getAttributeNameResource(int i);

    private native String n_getAttributeNamespace(int i);

    private native String n_getAttributePrefix(int i);

    private native int n_getAttributeResourceValue(int i, int i2);

    private native int n_getAttributeResourceValue(String str, String str2, int i);

    private native String n_getAttributeType(int i);

    private native int n_getAttributeUnsignedIntValue(int i, int i2);

    private native int n_getAttributeUnsignedIntValue(String str, String str2, int i);

    private native String n_getAttributeValue(int i);

    private native String n_getAttributeValue(String str, String str2);

    private native String n_getClassAttribute();

    private native int n_getColumnNumber();

    private native int n_getDepth();

    private native int n_getEventType();

    private native boolean n_getFeature(String str);

    private native String n_getIdAttribute();

    private native int n_getIdAttributeResourceValue(int i);

    private native String n_getInputEncoding();

    private native int n_getLineNumber();

    private native String n_getName();

    private native String n_getNamespace();

    private native String n_getNamespace(String str);

    private native int n_getNamespaceCount(int i);

    private native String n_getNamespacePrefix(int i);

    private native String n_getNamespaceUri(int i);

    private native String n_getPositionDescription();

    private native String n_getPrefix();

    private native Object n_getProperty(String str);

    private native int n_getStyleAttribute();

    private native String n_getText();

    private native char[] n_getTextCharacters(int[] iArr);

    private native boolean n_isAttributeDefault(int i);

    private native boolean n_isEmptyElementTag();

    private native boolean n_isWhitespace();

    private native int n_next();

    private native int n_nextTag();

    private native String n_nextText();

    private native int n_nextToken();

    private native void n_require(int i, String str, String str2);

    private native void n_setFeature(String str, boolean z);

    private native void n_setInput(InputStream inputStream, String str);

    private native void n_setInput(Reader reader);

    private native void n_setProperty(String str, Object obj);

    static {
        Runtime.register("Android.Runtime.XmlReaderResourceParser, Mono.Android", XmlReaderResourceParser.class, __md_methods);
    }

    public XmlReaderResourceParser() {
        if (getClass() == XmlReaderResourceParser.class) {
            TypeManager.Activate("Android.Runtime.XmlReaderResourceParser, Mono.Android", "", this, new Object[0]);
        }
    }

    @Override // android.content.res.XmlResourceParser, java.lang.AutoCloseable
    public void close() {
        n_close();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getAttributeNamespace(int i) {
        return n_getAttributeNamespace(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int getAttributeCount() {
        return n_getAttributeCount();
    }

    @Override // android.util.AttributeSet
    public String getClassAttribute() {
        return n_getClassAttribute();
    }

    @Override // android.util.AttributeSet
    public String getIdAttribute() {
        return n_getIdAttribute();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getPositionDescription() {
        return n_getPositionDescription();
    }

    @Override // android.util.AttributeSet
    public int getStyleAttribute() {
        return n_getStyleAttribute();
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(int i, boolean z) {
        return n_getAttributeBooleanValue(i, z);
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(String str, String str2, boolean z) {
        return n_getAttributeBooleanValue(str, str2, z);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(int i, float f) {
        return n_getAttributeFloatValue(i, f);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(String str, String str2, float f) {
        return n_getAttributeFloatValue(str, str2, f);
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(int i, int i2) {
        return n_getAttributeIntValue(i, i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(String str, String str2, int i) {
        return n_getAttributeIntValue(str, str2, i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(int i, String[] strArr, int i2) {
        return n_getAttributeListValue(i, strArr, i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(String str, String str2, String[] strArr, int i) {
        return n_getAttributeListValue(str, str2, strArr, i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getAttributeName(int i) {
        return n_getAttributeName(i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeNameResource(int i) {
        return n_getAttributeNameResource(i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(int i, int i2) {
        return n_getAttributeResourceValue(i, i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(String str, String str2, int i) {
        return n_getAttributeResourceValue(str, str2, i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(int i, int i2) {
        return n_getAttributeUnsignedIntValue(i, i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(String str, String str2, int i) {
        return n_getAttributeUnsignedIntValue(str, str2, i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(int i) {
        return n_getAttributeValue(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(String str, String str2) {
        return n_getAttributeValue(str, str2);
    }

    @Override // android.util.AttributeSet
    public int getIdAttributeResourceValue(int i) {
        return n_getIdAttributeResourceValue(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        return n_getColumnNumber();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        return n_getDepth();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int getEventType() {
        return n_getEventType();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getInputEncoding() {
        return n_getInputEncoding();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() {
        return n_isEmptyElementTag();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() {
        return n_isWhitespace();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        return n_getLineNumber();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getName() {
        return n_getName();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getNamespace() {
        return n_getNamespace();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getPrefix() {
        return n_getPrefix();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getText() {
        return n_getText();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(String str, String str2) {
        n_defineEntityReplacementText(str, str2);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getAttributePrefix(int i) {
        return n_getAttributePrefix(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getAttributeType(int i) {
        return n_getAttributeType(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public boolean getFeature(String str) {
        return n_getFeature(str);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getNamespace(String str) {
        return n_getNamespace(str);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) {
        return n_getNamespaceCount(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getNamespacePrefix(int i) {
        return n_getNamespacePrefix(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String getNamespaceUri(int i) {
        return n_getNamespaceUri(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public Object getProperty(String str) {
        return n_getProperty(str);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public char[] getTextCharacters(int[] iArr) {
        return n_getTextCharacters(iArr);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public boolean isAttributeDefault(int i) {
        return n_isAttributeDefault(i);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int next() {
        return n_next();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int nextTag() {
        return n_nextTag();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public String nextText() {
        return n_nextText();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public int nextToken() {
        return n_nextToken();
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public void require(int i, String str, String str2) {
        n_require(i, str, str2);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public void setFeature(String str, boolean z) {
        n_setFeature(str, z);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public void setInput(InputStream inputStream, String str) {
        n_setInput(inputStream, str);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public void setInput(Reader reader) {
        n_setInput(reader);
    }

    @Override // android.runtime.XmlReaderPullParser, org.xmlpull.v1.XmlPullParser
    public void setProperty(String str, Object obj) {
        n_setProperty(str, obj);
    }

    @Override // android.runtime.XmlReaderPullParser, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // android.runtime.XmlReaderPullParser, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
