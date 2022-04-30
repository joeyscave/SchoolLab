#include <sys/socket.h>
#include <sys/un.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <errno.h>
#include <pthread.h>
#include <stdlib.h>

#define PORT 8000                       // �����������˿�

void send_handle(int* client_socket){
        char status[] = "HTTP/1.0 200 OK\r\n";
    char header[] = "Server: DWBServer\r\nContent-Type: text/html;charset=utf-8\r\n\r\n";
    char body[] = "<html><head><title>C���Թ���С��Web������</title></head><body><h2>��ӭ</h2><p>Hello��World</p></body></html>";

        printf("enter sleeping... \n");
        sleep(10);    //���߳̽���˯��״̬����λ���롣
        printf("finish sleeping... \n");

    write(*client_socket, status, sizeof(status));
    write(*client_socket, header, sizeof(header));
    write(*client_socket, body, sizeof(body));

    close(*client_socket);
}

int main(){

    int server_socket = socket(AF_INET, SOCK_STREAM, 0);     //��ʼ���׽���
    struct sockaddr_in server_addr;

    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;     // �����ַ�Ͷ˿�����
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    server_addr.sin_port = htons(PORT);

    bind(server_socket, (struct sockaddr *)&server_addr, sizeof(server_addr));   //�����ص�ַ�󶨵����������׽�����

    listen(server_socket, 5);      //��ʼ�����Ƿ��пͻ������ӣ��ڶ�����������������
    
    int threadNum = 1;      //�߳�����
    
    while(1)
    {
        char buf[1024];
        int client_socket;
        printf("======waiting for client's request %d=====\n",threadNum);
        if((client_socket=accept(server_socket,(struct sockaddr*)NULL,NULL))==-1){  //�ȴ��ͻ��ˣ������������
            printf("accept socket error :%s(errno:%d)\n",strerror(errno),errno);
            continue;
        }
        printf("======waiting for read request data %d=====\n",threadNum);
        read(client_socket, buf, 1024);                         //��ȡ�ͻ������ݣ�������HTTP����������

        //�����߳�  
        int ret = pthread_create((pthread_t*)malloc(sizeof(pthread_t)), NULL, (void *)send_handle, (int*)malloc(sizeof(int)));	
        if (ret != 0) {
        perror("Occur error when create new thread\n");
        exit(1);
        }
	else threadNum++;
	pthread_detach(pthread_self());	//�����߳�
    }
    close(server_socket);

    return 0;
}
                                    

