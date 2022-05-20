
#已当前i 为原点，将他的3元组4元组一直到8元组的向量相加，最后七个按照7，6，5，4，3相组，所以总共325行
# H1N1_seq = r'./squence/H3N2_sequence_HA1.csv'
# H1N1_anti =r'./antigenic/H3N2_antigenic.csv'
H1N1_seq = r'./squence/H5N1_sequence_HA1.csv'
H1N1_anti =r'./antigenic/H5N1_antigenic.csv'
f1 = open(H1N1_seq,'r')
f2 = open(H1N1_anti,'r')
list_Name_anti = f2.readlines()

#抗原数据导入缓存
list_H1N1_One_anti = []
for i in range(0, len(list_Name_anti)):
    list_Name_anti[i] = list_Name_anti[i].replace('\n','')
    list_Name_anti[i] = list_Name_anti[i].upper()   #菌株名全部大写
# print(list_Name_anti)
for line in list_Name_anti:
    # line =line.replace('\n','')
    list_H1N1_One_anti.append(line.strip().split(',')) #list_H1N1_One是根据逗号分割为三部分 (抗原距离文件）
del list_H1N1_One_anti[0]#删除第一列
print(list_H1N1_One_anti)
# {
#       "source": "Courfeyrac",
#       "target": "Combeferre",
#       "value": 13
#  },
for line in list_H1N1_One_anti:
    source = '"'+line[0]+'"'
    target = '"' + line[1] + '"'
    print('{"source":',source,',"target":',target,',"value":',line[2],'},')
#除掉\n

############################
list_Name = f1.readlines()

list_H1N1_One = [] #294
for line in list_Name:
    list_H1N1_One.append(line.strip().split(',')) #list_H1N1_One是根据逗号分割为两部分

for i in range(0,len(list_H1N1_One)):
    list_H1N1_One[i][1]=list_H1N1_One[i][1].upper()  #菌株名全部大写


# print(list_H1N1_One)
H1N1_dict = {}
for line in list_H1N1_One:  #line[1]是病毒名字  初始值  长度是329/8=41---1
    H1N1_dict[line[1]] = 3   #形成键值对
# 关于原点的大小，出度+3，入度+2
# print(H1N1_dict)
for line in list_H1N1_One_anti:
    H1N1_dict[line[0]] = H1N1_dict[line[0]] + 0.5
    H1N1_dict[line[1]] = H1N1_dict[line[1]] + 0.3
    # print(H1N1_dict[line[0]])
# print(H1N1_dict)
# for i in H1N1_dict:
#     num = int(H1N1_dict[i])
#     nodeName = '"'+i+'"'
#     print('{"id":',nodeName,',"label":',nodeName,',"size":',num,'},')


