#version 410

in vec2 vertices;
in vec3 colour;
in vec2 t;
out vec2 tex;
out vec3 c_out;

uniform mat4 viewMatrix; //the camera, i.e. moves the world
uniform mat4 projectionMatrix; //scales/rotates the whole world

const vec2 lookup[6] = vec2[]( vec2(0,0),
                        vec2(0,-0.1),
                        vec2(0.1,0),
                        vec2(0,-0.1),
                        vec2(0.1,-0.1),
                        vec2(0.1,0)
                       );

void main(void){
    int cell = gl_VertexID/6;
    
    int v = gl_VertexID%6;
    vec2 pos;
    int width = 20;
    
    float size = 2.0/width;
    
    int row = cell/width;
    int column = cell%width;
    
    vec2 init = vec2(-1+column*size,1-row*size);
       


    
//    switch(v)
//    {
//        case 0: pos = vec2(-1+column*size,1-row*size); break;
//        case 1: pos = vec2(-1+column*size,1-(row+1)*size); break;
//        case 2: pos = vec2(-1+(column+1)*size,1-row*size); break;
//        case 3: pos = vec2(-1+column*size,1-(row+1)*size); break;
//        case 4: pos = vec2(-1+(column+1)*size,1-(row+1)*size); break;
//        case 5: pos = vec2(-1+(column+1)*size,1-row*size); break;
//    }
    gl_Position = vec4(init+lookup[v],0.0,1.0);

    //gl_Position =   projectionMatrix * viewMatrix *  vec4(vertices.xy,0.0,1.0);
    //c_out = colour;
    //tex = t;
}