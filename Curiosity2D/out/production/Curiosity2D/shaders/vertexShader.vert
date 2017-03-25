#version 410

in vec2 vertices;
in vec3 colour;
in vec2 t;
out vec2 tex;
out vec3 c_out;

uniform mat4 viewMatrix; //the camera, i.e. moves the world
uniform mat4 projectionMatrix; //scales/rotates the whole world



void main(void){
    int cell = gl_VertexID/6;
    int v = gl_VertexID%6;
    vec2 pos;
    float size = 0.8;
    switch(v)
    {
        case 0: pos = vec2(-size,size); break;
        case 1: pos = vec2(-size,-size); break;
        case 2: pos = vec2(size,size); break;
        case 3: pos = vec2(-size,-size); break;
        case 4: pos = vec2(size,-size); break;
        case 5: pos = vec2(size,size); break;
    }
    gl_Position = vec4(pos,0.0,1.0);

    //gl_Position =   projectionMatrix * viewMatrix *  vec4(vertices.xy,0.0,1.0);
    //c_out = colour;
    //tex = t;
}