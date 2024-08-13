// 声明 proto 语法版本，固定值

// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.34.2
// 	protoc        v3.15.5
// source: democ.proto

// proto 包名

package __

import (
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

// 定义请求体
type CSayHelloReq struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Name     string `protobuf:"bytes,1,opt,name=name,proto3" json:"name,omitempty"`
	CallType string `protobuf:"bytes,2,opt,name=callType,proto3" json:"callType,omitempty"`
}

func (x *CSayHelloReq) Reset() {
	*x = CSayHelloReq{}
	if protoimpl.UnsafeEnabled {
		mi := &file_democ_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *CSayHelloReq) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*CSayHelloReq) ProtoMessage() {}

func (x *CSayHelloReq) ProtoReflect() protoreflect.Message {
	mi := &file_democ_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use CSayHelloReq.ProtoReflect.Descriptor instead.
func (*CSayHelloReq) Descriptor() ([]byte, []int) {
	return file_democ_proto_rawDescGZIP(), []int{0}
}

func (x *CSayHelloReq) GetName() string {
	if x != nil {
		return x.Name
	}
	return ""
}

func (x *CSayHelloReq) GetCallType() string {
	if x != nil {
		return x.CallType
	}
	return ""
}

// 定义响应体
type CSayHelloResp struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Message   string `protobuf:"bytes,1,opt,name=message,proto3" json:"message,omitempty"`
	CallChain string `protobuf:"bytes,2,opt,name=callChain,proto3" json:"callChain,omitempty"`
}

func (x *CSayHelloResp) Reset() {
	*x = CSayHelloResp{}
	if protoimpl.UnsafeEnabled {
		mi := &file_democ_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *CSayHelloResp) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*CSayHelloResp) ProtoMessage() {}

func (x *CSayHelloResp) ProtoReflect() protoreflect.Message {
	mi := &file_democ_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use CSayHelloResp.ProtoReflect.Descriptor instead.
func (*CSayHelloResp) Descriptor() ([]byte, []int) {
	return file_democ_proto_rawDescGZIP(), []int{1}
}

func (x *CSayHelloResp) GetMessage() string {
	if x != nil {
		return x.Message
	}
	return ""
}

func (x *CSayHelloResp) GetCallChain() string {
	if x != nil {
		return x.CallChain
	}
	return ""
}

var File_democ_proto protoreflect.FileDescriptor

var file_democ_proto_rawDesc = []byte{
	0x0a, 0x0b, 0x64, 0x65, 0x6d, 0x6f, 0x63, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x05, 0x67,
	0x72, 0x65, 0x65, 0x74, 0x22, 0x3e, 0x0a, 0x0c, 0x43, 0x53, 0x61, 0x79, 0x48, 0x65, 0x6c, 0x6c,
	0x6f, 0x52, 0x65, 0x71, 0x12, 0x12, 0x0a, 0x04, 0x6e, 0x61, 0x6d, 0x65, 0x18, 0x01, 0x20, 0x01,
	0x28, 0x09, 0x52, 0x04, 0x6e, 0x61, 0x6d, 0x65, 0x12, 0x1a, 0x0a, 0x08, 0x63, 0x61, 0x6c, 0x6c,
	0x54, 0x79, 0x70, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x08, 0x63, 0x61, 0x6c, 0x6c,
	0x54, 0x79, 0x70, 0x65, 0x22, 0x47, 0x0a, 0x0d, 0x43, 0x53, 0x61, 0x79, 0x48, 0x65, 0x6c, 0x6c,
	0x6f, 0x52, 0x65, 0x73, 0x70, 0x12, 0x18, 0x0a, 0x07, 0x6d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65,
	0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x07, 0x6d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x12,
	0x1c, 0x0a, 0x09, 0x63, 0x61, 0x6c, 0x6c, 0x43, 0x68, 0x61, 0x69, 0x6e, 0x18, 0x02, 0x20, 0x01,
	0x28, 0x09, 0x52, 0x09, 0x63, 0x61, 0x6c, 0x6c, 0x43, 0x68, 0x61, 0x69, 0x6e, 0x32, 0x42, 0x0a,
	0x08, 0x43, 0x47, 0x72, 0x65, 0x65, 0x74, 0x65, 0x72, 0x12, 0x36, 0x0a, 0x09, 0x43, 0x53, 0x61,
	0x79, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x12, 0x13, 0x2e, 0x67, 0x72, 0x65, 0x65, 0x74, 0x2e, 0x43,
	0x53, 0x61, 0x79, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x52, 0x65, 0x71, 0x1a, 0x14, 0x2e, 0x67, 0x72,
	0x65, 0x65, 0x74, 0x2e, 0x43, 0x53, 0x61, 0x79, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x52, 0x65, 0x73,
	0x70, 0x42, 0x04, 0x5a, 0x02, 0x2e, 0x2f, 0x62, 0x06, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x33,
}

var (
	file_democ_proto_rawDescOnce sync.Once
	file_democ_proto_rawDescData = file_democ_proto_rawDesc
)

func file_democ_proto_rawDescGZIP() []byte {
	file_democ_proto_rawDescOnce.Do(func() {
		file_democ_proto_rawDescData = protoimpl.X.CompressGZIP(file_democ_proto_rawDescData)
	})
	return file_democ_proto_rawDescData
}

var file_democ_proto_msgTypes = make([]protoimpl.MessageInfo, 2)
var file_democ_proto_goTypes = []any{
	(*CSayHelloReq)(nil),  // 0: greet.CSayHelloReq
	(*CSayHelloResp)(nil), // 1: greet.CSayHelloResp
}
var file_democ_proto_depIdxs = []int32{
	0, // 0: greet.CGreeter.CSayHello:input_type -> greet.CSayHelloReq
	1, // 1: greet.CGreeter.CSayHello:output_type -> greet.CSayHelloResp
	1, // [1:2] is the sub-list for method output_type
	0, // [0:1] is the sub-list for method input_type
	0, // [0:0] is the sub-list for extension type_name
	0, // [0:0] is the sub-list for extension extendee
	0, // [0:0] is the sub-list for field type_name
}

func init() { file_democ_proto_init() }
func file_democ_proto_init() {
	if File_democ_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_democ_proto_msgTypes[0].Exporter = func(v any, i int) any {
			switch v := v.(*CSayHelloReq); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_democ_proto_msgTypes[1].Exporter = func(v any, i int) any {
			switch v := v.(*CSayHelloResp); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_democ_proto_rawDesc,
			NumEnums:      0,
			NumMessages:   2,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_democ_proto_goTypes,
		DependencyIndexes: file_democ_proto_depIdxs,
		MessageInfos:      file_democ_proto_msgTypes,
	}.Build()
	File_democ_proto = out.File
	file_democ_proto_rawDesc = nil
	file_democ_proto_goTypes = nil
	file_democ_proto_depIdxs = nil
}
