# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
# LIC_FILES_CHKSUM = "file://../LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

# Has to be github.com/ and not github.com:
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-spencermanning.git;protocol=ssh;branch=master \
			file://aesd-char-driver_init \
			"

# KEEP THIS UP TO DATE WITH ASY3
SRCREV = "ddfe433f278a5091d8cdc7ad3be8c4256d4069fa"
# Modify these as desired
PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-char-driver_init"

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/aesdchar_load"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/aesdchar_unload"
# FILES:${PN} += "${base_libdir}/modules/5.15.18"

do_configure () {
	:
}

do_compile () {
    # NEED to include the "-C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver" stuff so the aesd-char-driver Makefile can be found!
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver
}

# KERNEL_VERSION = "5.15.124-yocto-standard"

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

	install -d ${D}${sysconfdir}/init.d
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${WORKDIR}/aesd-char-driver_init ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesd-char-driver/aesdchar_unload ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/aesd-char-driver/aesdchar_load ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/aesd-char-driver/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/

}