from rest_framework import serializers
from authApp.models.user import User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'username', 'password', 'name','documento','fecha_expedicion','fecha_nacimiento','grupo_sanguineo','rh' 'email']
